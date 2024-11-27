package CODEDBTA.GenerationsBank.entity;

import CODEDBTA.GenerationsBank.enums.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.LocalDate;


@Entity
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    private double amount;

    private String transactionFrom;
    private String transactionTo;
    private LocalDate date;
    private String category;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @ManyToOne
    private AccountEntity account;

    private boolean isApproved;

    private LocalDateTime timeStamp;


    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransactionFrom() {
        return transactionFrom;
    }

    public void setTransactionFrom(String transactionFrom) {
        this.transactionFrom = transactionFrom;
    }

    public String getTransactionTo() {
        return transactionTo;
    }

    public void setTransactionTo(String transactionTo) {
        this.transactionTo = transactionTo;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }
}