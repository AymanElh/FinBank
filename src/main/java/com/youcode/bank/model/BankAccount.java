package com.youcode.bank.model;

import java.util.List;
import java.util.UUID;

public abstract class BankAccount {
    private String accountId;
    private double balance;
    private String currency;
    private AccountStatus status;
    private Client owner;
    private List<Transaction> historyOfTransactions;

    public BankAccount() {
        this.accountId = UUID.randomUUID().toString();
        this.status = AccountStatus.CREATED;
    }

    public BankAccount(double balance, String currency) {
        this();
        this.balance = balance;
        this.currency = currency;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }
}
