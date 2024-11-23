package CODEDBTA.GenerationsBank.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;
    private Date date;

    @ManyToOne
    private UserEntity transactionFrom;

    @ManyToOne
    private UserEntity transactionTo;

    @ManyToOne
    private AccountEntity account;


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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public UserEntity getTransactionFrom() {
        return transactionFrom;
    }

    public void setTransactionFrom(UserEntity transactionFrom) {
        this.transactionFrom = transactionFrom;
    }

    public UserEntity getTransactionTo() {
        return transactionTo;
    }

    public void setTransactionTo(UserEntity transactionTo) {
        this.transactionTo = transactionTo;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }
}