package CODEDBTA.GenerationsBank.bo.admin;

import CODEDBTA.GenerationsBank.entity.TransactionEntity;

public class GetTransactionResponse {

    private String message;
    private TransactionEntity transaction;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TransactionEntity getTransaction() {
        return transaction;
    }

    public void setTransaction(TransactionEntity transaction) {
        this.transaction = transaction;
    }
}
