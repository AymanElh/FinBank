package com.youcode.bank.exceptions;

public class InvalidAmountException extends RuntimeException {
    public InvalidAmountException(double amount) {
        super("Invalid amount " + amount + ". Amount should be positive");
    }
}
