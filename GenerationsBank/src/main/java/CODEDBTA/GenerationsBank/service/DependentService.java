package CODEDBTA.GenerationsBank.service;

import CODEDBTA.GenerationsBank.entity.TransactionEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DependentService {
    void transferDependent(Long fromId, Long toId, double amount);
    List<TransactionEntity> viewTransactions(Long dependentAccountId);

}
