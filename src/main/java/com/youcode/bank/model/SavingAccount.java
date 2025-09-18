package com.youcode.bank.model;

public class SavingAccount extends BankAccount {
    private double interestRate;

    public SavingAccount(double balance, Client client) {
        super(balance, client);
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
}
