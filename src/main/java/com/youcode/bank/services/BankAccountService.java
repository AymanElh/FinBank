package com.youcode.bank.services;

import com.youcode.bank.exceptions.AccountIsAlreadyExistException;
import com.youcode.bank.exceptions.AccountNotFoundException;
import com.youcode.bank.model.BankAccount;
import com.youcode.bank.model.Client;
import com.youcode.bank.model.CurrentAccount;
import com.youcode.bank.model.SavingAccount;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class BankAccountService {
    ArrayList<BankAccount> bankAccounts = new ArrayList<>();
    private static int accountCounter = 1000;

    public BankAccountService() {
        loadAccountCounter();
    }

    public BankAccount addBankAccount(String accountType, double balance, Client client) throws AccountIsAlreadyExistException {
        boolean hasExistingAccount = bankAccounts.stream()
                .anyMatch(account -> account.getOwner().getUserId().equals(client.getUserId())
                        && account.getClass().getSimpleName().toLowerCase().contains(accountType.split("-")[0]));

        if (hasExistingAccount) {
            throw new AccountIsAlreadyExistException("Client already has a " + accountType + " account");
        }

        BankAccount acc = null;
        if (accountType.equals("saving-account")) {
            acc = this.createSavingAccount(balance, client);
            bankAccounts.add(acc);
        } else if (accountType.equals("current-account")) {
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

    public BankAccount getAccountById(String accountId) throws AccountNotFoundException {
        return bankAccounts
                .stream()
                .filter(account -> account.getAccountId().equals(accountId))
                .findFirst()
                .orElseThrow(() -> new AccountNotFoundException("Account with id " + accountId + " Not found"));
    }

    public List<BankAccount> getClientAccounts(Client client) {
        return bankAccounts.stream()
                .filter(account -> account.getOwner().getUserId().equals(client.getUserId()))
                .collect(Collectors.toList());
    }

    private void loadAccountCounter() {
        File counterFile = new File("account_counter.txt");
        if (counterFile.exists()) {
            try (Scanner scanner = new Scanner(counterFile)) {
                if (scanner.hasNextInt()) {
                    accountCounter = scanner.nextInt();
                }
            } catch (FileNotFoundException e) {
                accountCounter = 1000; // Default start
            }
        }
    }

    private void saveAccountCounter() {
        try (FileWriter writer = new FileWriter("account_counter.txt")) {
            writer.write(String.valueOf(accountCounter));
        } catch (IOException e) {
            System.out.println("Warning: Could not save account counter");
        }
    }
}
