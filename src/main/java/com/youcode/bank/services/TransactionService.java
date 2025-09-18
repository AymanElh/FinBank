package com.youcode.bank.services;

import com.youcode.bank.model.BankAccount;
import com.youcode.bank.model.Transaction;
import com.youcode.bank.model.TransactionType;

import java.util.Date;
import java.util.List;

public class TransactionService {
    public static int transactionCounter = 1;

    public Transaction createTransaction(TransactionType type, double amount) {
        String transactionId = "T" + String.format("%04d", transactionCounter++);

        Transaction transaction = new Transaction(transactionId, type, amount);
        transaction.setTransactionDate(new Date());
        return transaction;
    }


    public boolean processDeposit(BankAccount account, double amount) {
        if (amount <= 0) {
            System.out.println("Amount cannot be negative");
            return false;
        }

        Transaction transaction = createTransaction(TransactionType.DEPOSIT, amount);
        account.setBalance(account.getBalance() + amount);

        account.getHistoryOfTransactions().add(transaction);

        System.out.println("Deposit of " + amount + " success");
        return true;
    }

    public boolean proceessWithdrawl(BankAccount account, double amount) {
        if (amount <= 0) {
            System.out.println("Amount should be positive");
            return false;
        }

        if (account.getBalance() < amount) {
            System.out.println("Balance not enough");
            // throw exception
            return false;
        }

        Transaction transaction = createTransaction(TransactionType.WITHDRAWAL, amount);

        account.setBalance(account.getBalance() - amount);
        account.getHistoryOfTransactions().add(transaction);
        System.out.println("Withdrawl successfully");

        return true;
    }

    public boolean processTransfer(BankAccount fromAccount, BankAccount toAccount, double amount) {
        if (amount <= 0) {
            System.out.println("Error amount should be positive");
            return false;
        }

        if (fromAccount.getBalance() < amount) {
            System.out.println("error: Sold insufficient");
            return false;
        }

        Transaction transaction = createTransaction(TransactionType.TRANSFER, amount);

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        fromAccount.getHistoryOfTransactions().add(transaction);
        toAccount.getHistoryOfTransactions().add(transaction);

        System.out.println("Virement de " + amount + " effect aver success");
        return true;
    }

    public void displayTransactionHistory(BankAccount account) {
        List<Transaction> transactions = account.getHistoryOfTransactions();

        if (transactions.isEmpty()) {
            System.out.println("No transaction for this account");
            return;
        }

        System.out.println("\n=== History of transactions ===");
        System.out.println("ID | Type | Amount | Date");
        System.out.println("--------------------------------");

        for (Transaction t : transactions) {
            System.out.printf("%s | %s | %.2f€ | %s%n",
                    t.getTransactionId(),
                    t.getType(),
                    t.getAmount(),
                    t.getTransactionDate()
            );
        }
        System.out.println("--------------------------------");
    }
}
