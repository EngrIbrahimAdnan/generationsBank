package CODEDBTA.GenerationsBank.service;

import CODEDBTA.GenerationsBank.bo.CreateUserRequest;
import CODEDBTA.GenerationsBank.bo.TransferRequest;
import CODEDBTA.GenerationsBank.entity.AccountEntity;
import CODEDBTA.GenerationsBank.entity.TransactionEntity;
import CODEDBTA.GenerationsBank.entity.UserEntity;
import CODEDBTA.GenerationsBank.enums.Roles;
import CODEDBTA.GenerationsBank.exception.InsufficientBalanceException;
import CODEDBTA.GenerationsBank.repository.AccountRepository;
import CODEDBTA.GenerationsBank.repository.TransactionRepository;
import CODEDBTA.GenerationsBank.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class GuardianServiceImpl implements GuardianService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final VerificationTokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;


    private UserEntity user;

    public GuardianServiceImpl(UserRepository userRepository, EmailService emailService, VerificationTokenService tokenService, PasswordEncoder passwordEncoder, AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public String CreateUserAccount(CreateUserRequest request) {

        String fieldMissing = validateFieldsOfRequest(request);

        // if a required field is missing, return missing field name
        if (fieldMissing != null){
            return fieldMissing;
        }

        // Ensure user has not registered with the same email address
        if (userRepository.findByEmail(request.getEmail())!= null){
            return "The email address '"+request.getEmail()+"' is already registered with."; // Return the name of the empty field
        }

        // Ensure user has not registered with the same username address
        if (userRepository.findByEmail(request.getUsername())!= null){
            return "The username '"+request.getUsername()+"' is already registered with."; // Return the name of the empty field
        }

        String token = tokenService.generateToken(request.getEmail().toLowerCase());

        try {
            emailService.sendVerificationEmail(request.getEmail().toLowerCase(), token, request.getUsername());
        } catch (MessagingException e) {
            return "Unable to send Verification email to the address provided. Please ensure it is entered correctly.";
        }

        Roles role;

        if (request.getRole() == null){
            role = Roles.GUARDIAN;
        }
        else {
            role = request.getRole();
        }

        // create userEntity and store to repository, ensuring verified variable is false pending email verification
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(request.getEmail().toLowerCase()); // toLowerCase() to ensure its case in-sensitive
        userEntity.setPassword(passwordEncoder.encode(request.getPassword())); // Abdulrahman: Encoded password using Bcrypt
        userEntity.setName(request.getUsername());//case-sensitive to make it personalized
        userEntity.setAge(request.getAge());
        userEntity.setAddress(request.getAddress());
        userEntity.setPhoneNumber(request.getPhoneNumber());
        userEntity.setVerified(false);//by default, the user is unverified. only after verification via email is this turned true
        userEntity.setRole(role);// defaults to guardian for time being
        userRepository.save(userEntity);

        // If no empty fields are found, return null to indicate all fields are valid
        return null;
    }

    @Override
    public String validateFieldsOfRequest(CreateUserRequest request){
        // Create a Set to store the field names you want to skip (e.g., "age")
        Set<String> fieldsToSkip = new HashSet<>();
        fieldsToSkip.add("age");  // Add the field you want to skip (e.g., "age")
        fieldsToSkip.add("role");

        // Iterate over all declared fields of the CreateUserRequest class
        for (var field : request.getClass().getDeclaredFields()) {
            // Make the private fields accessible for reflection
            field.setAccessible(true);

            // If the field name is in the 'fieldsToSkip' set, skip this field and continue to the next one
            if (fieldsToSkip.contains(field.getName())) {
                continue; // Skip this field and move to the next iteration
            }

            try {
                // Get the value of the current field from the request object
                Object value = field.get(request);

                // If the field is null or an empty string, return the name of the empty field
                if (value == null || (value instanceof String && ((String) value).isEmpty())) {
                    return "The field '" + field.getName() + "' is required and cannot be empty."; // Return the name of the empty field
                }
            } catch (IllegalAccessException e) {
                // Handle any reflection-related access exceptions (e.g., if the field is not accessible)
                e.printStackTrace();
            }
        }
        // if all fields are satisfied, return null
        return null;
    }

    @Override
    public TransactionEntity transfer(TransferRequest transferRequest) {
        //Getting the input parameters
        Long senderAccountId = transferRequest.getSenderAccountId();
        Long receiverAccountId = transferRequest.getReceiverAccountId();
        double amount = transferRequest.getAmount();


        //Finding the account
        AccountEntity senderAccount = accountRepository.findById(senderAccountId)
                .orElseThrow(() -> new EntityNotFoundException("Sender account not found"));
        AccountEntity receiverAccount = accountRepository.findById(receiverAccountId)
                .orElseThrow(() -> new EntityNotFoundException("Recipient account not found"));


        //Error Checking
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be greater than zero");
        }
        if (senderAccount.getBalance() < amount) {
            throw new InsufficientBalanceException("Insufficient funds in the sender's account");
        }

        //Updating the balance
        senderAccount.setBalance(senderAccount.getBalance() - amount);
        receiverAccount.setBalance(receiverAccount.getBalance() + amount);

        //Saving the updated balance
        accountRepository.save(senderAccount);
        accountRepository.save(receiverAccount);

        //Logging the transaction
        TransactionEntity transaction = new TransactionEntity();
        transaction.setTransactionFromId(senderAccount.getId());
        transaction.setTransactionToId(receiverAccount.getId());
        transaction.setAmount(amount);
        transaction.setTimeStamp(LocalDateTime.now());
        transactionRepository.save(transaction);

        return transaction;
    }

    @Override
    public void addDependent(Long guardianAccountId, Long dependentAccountId) {
        UserEntity guardianId = userRepository.findById(guardianAccountId).orElseThrow(() -> new EntityNotFoundException("Guardian ID not found"));
        UserEntity dependentId = userRepository.findById(dependentAccountId).orElseThrow(() -> new EntityNotFoundException("Dependent ID not found"));

        


    }
}