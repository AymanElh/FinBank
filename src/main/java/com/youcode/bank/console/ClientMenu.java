package com.youcode.bank.console;

import com.youcode.bank.exceptions.AccountIsAlreadyExistException;
import com.youcode.bank.exceptions.AccountNotFoundException;
import com.youcode.bank.exceptions.InvalidAmountException;
import com.youcode.bank.model.BankAccount;
import com.youcode.bank.model.Client;
import com.youcode.bank.services.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ClientMenu {
    private final Client client;
    private final ClientService clientService;
    private final BankAccountService bankAccountService;
    private final TransactionService transactionService;
    private final MenuService menuService;

    private final List<MenuItem> CLIENT_MENU = Arrays.asList(
            new MenuItem(1, "Create new account"),
            new MenuItem(2, "Get my balance"),
            new MenuItem(3, "View all my accounts"),
            new MenuItem(4, "View all my transactions"),
            new MenuItem(5, "Make a deposit"),
            new MenuItem(6, "Make a withdrawal"),
            new MenuItem(7, "Make a transfer"),
            new MenuItem(8, "Exit")
    );

    public ClientMenu(Scanner sc, Client client, ClientService clientService, BankAccountService bankAccountService, TransactionService transactionService) {
        this.client = client;
        this.clientService = clientService;
        this.bankAccountService = bankAccountService;
        this.transactionService = transactionService;
        this.menuService = new MenuService(sc);
    }

    public void show() {
        while (true) {
            int choice = menuService.displayMenuAndGetChoice("Enter your choice: ", CLIENT_MENU);

            switch (choice) {
                case 1:
                    createAccount();
                    break;

                case 2:
                    try {
                        getBalance();
                    } catch (AccountNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 3:
                    viewAllAccounts();
                    break;
                case 4:
                    viewAccountTransactionHistory();
                    break;

                case 5:
                    makeDeposit();
                    break;

                case 6:
                    makeWithdrawal();
                    break;

                case 7:
                    makeTransfer();
                    break;

                case 8:
                    System.out.println("Logging out...");
                    return;


                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void createAccount() {
        List<MenuItem> accountTypes = Arrays.asList(
                new MenuItem(1, "Saving account"),
                new MenuItem(2, "Current account")
        );

        int accountChoice = menuService.displayMenuAndGetChoice("Enter your choice", accountTypes);
        double initialBalance = menuService.getDoubleInput("Enter initial balance");


        try {
            String type = accountChoice == 1 ? "saving-account" : "current-account";
            BankAccount acc = bankAccountService.addBankAccount(type, initialBalance, client);
            System.out.println("\n ** Your account created successfully ** \n \t your account:  " + acc);
        } catch (AccountIsAlreadyExistException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void getBalance() throws AccountNotFoundException {
        String accountId = menuService.getInputString("Enter your account Id");
        try {
            BankAccount acc = bankAccountService.getAccountById(accountId);
            System.out.println("Your balance is: " + acc.getBalance());
        } catch (AccountNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewAllAccounts() {
        List<BankAccount> accounts = bankAccountService.getClientAccounts(client);

        if (accounts.isEmpty()) {
            System.out.println("No accounts found");
        } else {
            System.out.println("\n=== Your Accounts ===");
            for (BankAccount acc : accounts) {
                System.out.println("ID: " + acc.getAccountId() + " | Balance: " + acc.getBalance() + "€");
            }
        }
    }

    private void makeDeposit() {
        String accountId = menuService.getInputString("Enter account ID");
        double amount = menuService.getDoubleInput("Enter deposit amount");

        try {
            BankAccount account = bankAccountService.getAccountById(accountId);
            transactionService.processDeposit(account, amount);
            System.out.println("Deposit completed successfully!");

        } catch (AccountNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void makeWithdrawal() {
        String accountId = menuService.getInputString("Enter account ID");
        double amount = menuService.getDoubleInput("Enter withdrawal amount");

        try {
            BankAccount account = bankAccountService.getAccountById(accountId);
            transactionService.proceessWithdrawl(account, amount);
            System.out.println("Withdrawal completed successfully!");
        } catch (AccountNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Withdrawal failed: " + e.getMessage());
        }
    }

    private void makeTransfer() {
        String fromAccountId = menuService.getInputString("Enter source account ID");
        String toAccountId = menuService.getInputString("Enter destination account ID");
        double amount = menuService.getDoubleInput("Enter transfer amount");

        try {
            BankAccount fromAccount = bankAccountService.getAccountById(fromAccountId);
            BankAccount toAccount = bankAccountService.getAccountById(toAccountId);
            transactionService.processTransfer(fromAccount, toAccount, amount);
            System.out.println("Transfer completed successfully!");
        } catch (AccountNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Transfer failed: " + e.getMessage());
        }
    }

    private void viewAccountTransactionHistory() {
        String accountId = menuService.getInputString("Enter your account id");
        try {
            BankAccount account = bankAccountService.getAccountById(accountId);
            transactionService.displayTransactionHistory(account);
        } catch (AccountNotFoundException e) {
            System.out.println("Error getting account: " + e.getMessage());
        }
    }

}
