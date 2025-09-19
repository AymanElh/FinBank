package com.youcode.bank.model;

import java.util.ArrayList;
import java.util.List;

public abstract class BankAccount {
    private static int accountSeq = 1000;
    private String accountId;
    private double balance;
    private AccountStatus status;
    private Client owner;
    private List<Transaction> historyOfTransactions = new ArrayList<>();

    public BankAccount() {
        this.accountId = generateAccountId();
        this.status = AccountStatus.CREATED;
    }

    public BankAccount(double balance, Client client) {
        this();
        this.balance = balance;
        this.owner = client;
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

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public String generateAccountId() {
        return "ACC-" + (accountSeq++);
    }

    public Client getOwner() {
        return owner;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }

    public List<Transaction> getHistoryOfTransactions() {
        return historyOfTransactions;
    }

    public void setHistoryOfTransactions(List<Transaction> historyOfTransactions) {
        this.historyOfTransactions = historyOfTransactions;
    }

    @Override
    public String toString() {
        return String.format("** Account info ** \n ID: %s, Balance: %.2f", this.accountId, this.balance);
    }
}
