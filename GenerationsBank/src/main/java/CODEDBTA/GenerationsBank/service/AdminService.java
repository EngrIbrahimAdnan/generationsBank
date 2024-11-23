package CODEDBTA.GenerationsBank.service;

import CODEDBTA.GenerationsBank.entity.AccountEntity;
import CODEDBTA.GenerationsBank.entity.TransactionEntity;
import CODEDBTA.GenerationsBank.entity.UserEntity;
import CODEDBTA.GenerationsBank.enums.Roles;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminService {
    List<UserEntity> getAllUsers();
    UserEntity getUserById(Long id);
    void deleteUserById(Long id);
    List<AccountEntity> getAllAccounts();
    AccountEntity getAccountById(Long id);
    void deleteAccountById(Long id);
    List<TransactionEntity> getAllTransactions();
    TransactionEntity getTransactionById(Long id);
    void deleteTransactionById(Long id);
    void assignRole(Long userId, Roles role);
}