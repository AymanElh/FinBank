package com.youcode.bank.model;

public class Transaction {
    private String transactionId;
    private TransactionType type;

    public Transaction(String transactionId, TransactionType type) {
        this.transactionId = transactionId;
        this.type = type;
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
}
