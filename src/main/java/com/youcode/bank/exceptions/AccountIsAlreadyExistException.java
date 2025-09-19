package com.youcode.bank.exceptions;

public class AccountIsAlreadyExistException extends Exception {
    public AccountIsAlreadyExistException(String message) {
        super(message);
    }
}
