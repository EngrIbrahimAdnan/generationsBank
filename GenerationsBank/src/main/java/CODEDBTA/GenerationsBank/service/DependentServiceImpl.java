package CODEDBTA.GenerationsBank.service;

import CODEDBTA.GenerationsBank.entity.AccountEntity;
import CODEDBTA.GenerationsBank.entity.TransactionEntity;
import CODEDBTA.GenerationsBank.enums.Roles;
import CODEDBTA.GenerationsBank.exception.InsufficientBalanceException;
import CODEDBTA.GenerationsBank.repository.AccountRepository;
import CODEDBTA.GenerationsBank.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class DependentServiceImpl implements DependentService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public DependentServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void transferDependent(Long fromId, Long toId, double amount) {
        //Finding the account
        AccountEntity dependentAccount = accountRepository.findById(fromId)
                .orElseThrow(() -> new EntityNotFoundException("Sender account not found"));

        if (amount > dependentAccount.getSpendingLimit()) {
            throw new IllegalArgumentException("Transfer amount exceeds the allowed spending limit.");
        }

        if (amount > dependentAccount.getMaxDaily()) {
            throw new IllegalArgumentException("Transfer amount exceeds the allowed maximum daily limit.");
        }

        if (amount > dependentAccount.getMaxWeekly()) {
            throw new IllegalArgumentException("Transfer amount exceeds the allowed maximum weekly limit.");
        }

        if (amount > dependentAccount.getMaxMonthly()) {
            throw new IllegalArgumentException("Transfer amount exceeds the allowed maximum monthly limit.");
        }

        LocalTime now = LocalTime.now();
        if (dependentAccount.getRestrictionStart() != null && dependentAccount.getRestrictionEnd() != null) {
            if (now.isAfter(dependentAccount.getRestrictionStart()) || now.isBefore(dependentAccount.getRestrictionEnd())) {
                throw new IllegalArgumentException("Transfers are not allowed during the restricted timeframe.");
            }
        }

        //Error Checking
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be greater than zero");
        }
        if (dependentAccount.getBalance() < amount) {
            throw new InsufficientBalanceException("Insufficient funds in the sender's account");
        }

        AccountEntity receiverAccount = accountRepository.findById(toId)
                .orElseThrow(() -> new EntityNotFoundException("Receiver account not found"));

        //Updating the balance
        if (dependentAccount.getUser().getRole().equals(Roles.DEPENDENT)) {
            dependentAccount.setBalance(dependentAccount.getBalance() - amount);
            receiverAccount.setBalance(receiverAccount.getBalance() + amount);

            //Saving the updated balance
            accountRepository.save(dependentAccount);
            accountRepository.save(receiverAccount);

            //Logging the transaction
            TransactionEntity transaction = new TransactionEntity();
            transaction.setTransactionFrom(dependentAccount.getUser().getName());
            transaction.setTransactionTo(receiverAccount.getUser().getName());
            transaction.setAccount(dependentAccount);
            transaction.setAmount(amount);
            transaction.setTimeStamp(LocalDateTime.now());
            transaction = transactionRepository.save(transaction);
            dependentAccount.addTransaction(transaction);
            accountRepository.save(dependentAccount);
        }
    }

    @Override
    public List<TransactionEntity> viewTransactions(Long dependentAccountId){
        AccountEntity dependentAccount = accountRepository.findById(dependentAccountId).orElseThrow(() -> new EntityNotFoundException("Dependent account not found"));
        return dependentAccount.getTransactions();
    }
}
