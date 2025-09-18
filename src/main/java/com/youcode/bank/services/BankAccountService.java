package com.youcode.bank.services;

import com.youcode.bank.model.BankAccount;
import com.youcode.bank.model.Client;
import com.youcode.bank.model.CurrentAccount;
import com.youcode.bank.model.SavingAccount;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BankAccountService {
    ArrayList<BankAccount> bankAccounts = new ArrayList<>();

    public BankAccount addBankAccount(String accountType, double balance, Client client) {
        BankAccount acc = null;
        if(accountType == "saving-account") {
            acc = this.createSavingAccount(balance, client);
            bankAccounts.add(acc);
        } else if (accountType == "current-account") {
            acc = this.createCurrentAccount(balance, client);
            bankAccounts.add(acc);
        }
        return acc;
    }

    private SavingAccount createSavingAccount(double initialBalance, Client client) {
        return new SavingAccount(initialBalance, client);
    }

    private CurrentAccount createCurrentAccount(double initialBalance, Client client) {
        return new CurrentAccount(initialBalance, client);
    }

    public List<BankAccount> getAllAccounts() {
        return bankAccounts;
    }



    public Optional<BankAccount> getAccountById(String accountId) {
        return bankAccounts
                .stream()
                .filter(account -> account.getAccountId().equals(accountId))
                .findFirst();
    }

    public List<BankAccount> getClientAccounts(Client client) {
        return bankAccounts.stream()
                .filter(account -> account.getOwner().getUserId().equals(client.getUserId()))
                .collect(Collectors.toList());
    }
}
