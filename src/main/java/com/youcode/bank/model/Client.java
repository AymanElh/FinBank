package com.youcode.bank.model;

import java.util.HashMap;

public class Client extends User {
    private HashMap<String, BankAccount> accounts;

    public Client(String name, String email, String password) {
        super(name, email, password);
    }
}
