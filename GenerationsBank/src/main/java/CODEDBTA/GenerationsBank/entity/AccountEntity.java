package CODEDBTA.GenerationsBank.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.List;

@Entity
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double balance;
    private double maxDaily;
    private double maxWeekly;
    private double maxMonthly;

    private double spendingLimit;

    private LocalTime restrictionStart;
    private LocalTime restrictionEnd;

    @OneToMany
    @JsonIgnoreProperties(value = {"account"})
    private List<TransactionEntity> transactions;

    @OneToOne
    private UserEntity user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<TransactionEntity> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionEntity> transactions) {
        this.transactions = transactions;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public double getSpendingLimit() {
        return spendingLimit;
    }

    public void setSpendingLimit(double spendingLimit) {
        this.spendingLimit = spendingLimit;
    }

    public double getMaxDaily() {
        return maxDaily;
    }

    public void setMaxDaily(double maxDaily) {
        this.maxDaily = maxDaily;
    }

    public double getMaxWeekly() {
        return maxWeekly;
    }

    public void setMaxWeekly(double maxWeekly) {
        this.maxWeekly = maxWeekly;
    }

    public double getMaxMonthly() {
        return maxMonthly;
    }

    public void setMaxMonthly(double maxMonthly) {
        this.maxMonthly = maxMonthly;
    }

    public LocalTime getRestrictionStart() {
        return restrictionStart;
    }

    public void setRestrictionStart(LocalTime restrictionStart) {
        this.restrictionStart = restrictionStart;
    }

    public LocalTime getRestrictionEnd() {
        return restrictionEnd;
    }

    public void setRestrictionEnd(LocalTime restrictionEnd) {
        this.restrictionEnd = restrictionEnd;
    }

    public List<TransactionEntity> addTransaction(TransactionEntity transaction){
        this.transactions.add(transaction);
        return transactions;
    }
}