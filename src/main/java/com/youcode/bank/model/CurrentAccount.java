package com.youcode.bank.model;

public class CurrentAccount extends BankAccount {
    private double overDraft;

    public CurrentAccount(double overDraft) {
        super();
        this.overDraft = overDraft;
    }

    public double getOverDraft() {
        return overDraft;
    }

    public void setOverDraft(double overDraft) {
        this.overDraft = overDraft;
    }
}
