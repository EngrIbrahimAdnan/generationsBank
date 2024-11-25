package CODEDBTA.GenerationsBank.entity;

import CODEDBTA.GenerationsBank.enums.TransactionStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.LocalDate;


@Entity
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;

    private Long transactionFromId;
    private Long transactionToId;
    private LocalDate date;
    private String category;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @ManyToOne
    private AccountEntity account;

    private LocalDateTime timeStamp;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Long getTransactionFromId() {
        return transactionFromId;
    }

    public void setTransactionFromId(Long transactionFromId) {
        this.transactionFromId = transactionFromId;
    }

    public Long getTransactionToId() {
        return transactionToId;
    }

    public void setTransactionToId(Long transactionToId) {
        this.transactionToId = transactionToId;
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
}