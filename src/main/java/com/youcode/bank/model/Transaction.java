package com.youcode.bank.model;

import java.util.Date;

public class Transaction {
    private String transactionId;
    private TransactionType type;
    private double amount;
    private Date transactionDate;

    public Transaction(String transactionId, TransactionType type, double amount) {
        this.transactionId = transactionId;
        this.type = type;
        this.amount = amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
}
