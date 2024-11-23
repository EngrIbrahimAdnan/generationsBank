package CODEDBTA.GenerationsBank.service;

import CODEDBTA.GenerationsBank.bo.CreateUserRequest;
import CODEDBTA.GenerationsBank.bo.TransferRequest;
import CODEDBTA.GenerationsBank.entity.TransactionEntity;
import CODEDBTA.GenerationsBank.entity.UserEntity;
import CODEDBTA.GenerationsBank.enums.Roles;
import CODEDBTA.GenerationsBank.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class GuardianServiceImpl implements GuardianService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final VerificationTokenService tokenService;
    private final PasswordEncoder passwordEncoder;


    private UserEntity user;

    public GuardianServiceImpl(UserRepository userRepository, EmailService emailService, VerificationTokenService tokenService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
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
            System.out.println(request.getEmail());
            return "The email address '"+request.getEmail()+"' is already registered with."; // Return the name of the empty field
        }

        String token = tokenService.generateToken(request.getEmail().toLowerCase());

        try {
            emailService.sendVerificationEmail(request.getEmail().toLowerCase(), token, request.getUsername());
        } catch (MessagingException e) {
            return "Unable to send Verification email to the address provided. Please ensure it is entered correctly.";
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
        userEntity.setRole(Roles.GUARDIAN);// defaults to guardian for time being
        userRepository.save(userEntity);

        // If no empty fields are found, return null to indicate all fields are valid
        return null;
    }

    @Override
    public String validateFieldsOfRequest(CreateUserRequest request){
        // Create a Set to store the field names you want to skip (e.g., "age")
        Set<String> fieldsToSkip = new HashSet<>();
        fieldsToSkip.add("age");  // Add the field you want to skip (e.g., "age")

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

        TransactionEntity transactionEntity = new TransactionEntity();
        return transactionEntity;
    }
}