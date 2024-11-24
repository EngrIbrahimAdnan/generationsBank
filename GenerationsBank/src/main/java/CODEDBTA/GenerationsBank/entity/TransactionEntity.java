package CODEDBTA.GenerationsBank.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;

    private Long transactionFromId;

    private Long transactionToId;

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
}