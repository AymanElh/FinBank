package com.youcode.bank.model;

public class CurrentAccount extends BankAccount {
    private double overDraft;

    public CurrentAccount(double balance, Client client) {
        super(balance, client);
    }

    public double getOverDraft() {
        return overDraft;
    }

    public void setOverDraft(double overDraft) {
        this.overDraft = overDraft;
    }
}
