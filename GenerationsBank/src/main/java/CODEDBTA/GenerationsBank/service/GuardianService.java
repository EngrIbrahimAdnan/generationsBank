package CODEDBTA.GenerationsBank.service;

import CODEDBTA.GenerationsBank.bo.CreateUserRequest;
import CODEDBTA.GenerationsBank.bo.guardian.AccountResponse;
import CODEDBTA.GenerationsBank.bo.guardian.RestrictionRequest;
import CODEDBTA.GenerationsBank.bo.guardian.TransactionResponse;
import CODEDBTA.GenerationsBank.entity.AccountEntity;
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
    List<TransactionResponse> viewTransactions(Long accountId, LocalDate startDate, LocalDate endDate, String category);
    void setSpendingLimit(Long dependentAccountId, double spendingLimit);
    void approveTransaction(Long transactionId, boolean approval);
    void setTransactionLimitDaily(Long dependentAccountId, double limit);
    void setTransactionLimitWeekly(Long dependentAccountId, double limit);
    void setTransactionLimitMonthly(Long dependentAccountId, double limit);
    void setTimeRestrictions(Long dependentAccountId, RestrictionRequest request);
    AccountResponse getAccountByUserId(Long accountId);
}