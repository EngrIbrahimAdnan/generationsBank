package CODEDBTA.GenerationsBank.service;

import CODEDBTA.GenerationsBank.bo.CreateUserRequest;
import CODEDBTA.GenerationsBank.bo.TransferRequest;
import CODEDBTA.GenerationsBank.entity.TransactionEntity;
import org.springframework.stereotype.Service;

@Service
public interface GuardianService {
    TransactionEntity transfer(TransferRequest transferRequest);
    String CreateUserAccount(CreateUserRequest request);
    String validateFieldsOfRequest(CreateUserRequest request);
}