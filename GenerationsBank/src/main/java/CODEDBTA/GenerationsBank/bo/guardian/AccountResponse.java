package CODEDBTA.GenerationsBank.bo.guardian;

import CODEDBTA.GenerationsBank.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalTime;

public class AccountResponse {
    private Long id;
    private double balance;
    private double maxDaily;
    private double maxWeekly;
    private double maxMonthly;
    private double spendingLimit;
    private LocalTime restrictionStart;
    private LocalTime restrictionEnd;
    @JsonIgnoreProperties(value = {"dependents"})
    private UserEntity user;

    public AccountResponse(Long id, double balance, double maxDaily, double maxWeekly, double maxMonthly, double spendingLimit, LocalTime restrictionStart, LocalTime restrictionEnd, UserEntity user) {
        this.id = id;
        this.balance = balance;
        this.maxDaily = maxDaily;
        this.maxWeekly = maxWeekly;
        this.maxMonthly = maxMonthly;
        this.spendingLimit = spendingLimit;
        this.restrictionStart = restrictionStart;
        this.restrictionEnd = restrictionEnd;
        this.user = user;
    }

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

    public double getSpendingLimit() {
        return spendingLimit;
    }

    public void setSpendingLimit(double spendingLimit) {
        this.spendingLimit = spendingLimit;
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

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
