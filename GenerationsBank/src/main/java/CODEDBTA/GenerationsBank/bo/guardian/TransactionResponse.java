package CODEDBTA.GenerationsBank.bo.guardian;

import CODEDBTA.GenerationsBank.entity.AccountEntity;
import CODEDBTA.GenerationsBank.enums.TransactionStatus;

import java.time.LocalDateTime;

public class TransactionResponse  {
    private Long transactionId;
    private String sentFromUser;
    private String sentToUser;
    private double amount;
    private TransactionStatus status;
    private LocalDateTime dateTime;

    public TransactionResponse(Long transactionId, String sentFromUser, String sentToUser, double amount, TransactionStatus status, LocalDateTime dateTime) {
        this.transactionId = transactionId;
        this.sentFromUser = sentFromUser;
        this.sentToUser = sentToUser;
        this.amount = amount;
        this.status = status;
        this.dateTime = dateTime;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getSentFromUser() {
        return sentFromUser;
    }

    public void setSentFromUser(String sentFromUser) {
        this.sentFromUser = sentFromUser;
    }

    public String getSentToUser() {
        return sentToUser;
    }

    public void setSentToUser(String sentToUser) {
        this.sentToUser = sentToUser;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
