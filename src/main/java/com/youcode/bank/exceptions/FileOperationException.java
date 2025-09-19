package com.youcode.bank.exceptions;

public class FileOperationException extends RuntimeException {
    public FileOperationException(String message) {
        super(message);
    }
}
