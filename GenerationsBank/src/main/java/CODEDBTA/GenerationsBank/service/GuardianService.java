package CODEDBTA.GenerationsBank.service;

import CODEDBTA.GenerationsBank.bo.CreateUserRequest;
import CODEDBTA.GenerationsBank.entity.TransactionEntity;
import CODEDBTA.GenerationsBank.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface GuardianService {
    void transfer(Long fromId, Long toId, double amount);
    String CreateUserAccount(CreateUserRequest request);
    String validateFieldsOfRequest(CreateUserRequest request);
    void addDependent(Long guardianAccountId, Long dependentAccountId);
    List<UserEntity> viewDependents(Long guardianId);
    List<TransactionEntity> viewTransactions(Long guardianId, LocalDate startDate, LocalDate endDate, String category);
    void setSpendingLimit(Long dependentAccountId, double spendingLimit);
    void approveTransaction(Long transactionId, boolean approval);
    TransactionEntity createTransaction(Long dependentAccountId, double amount);
}