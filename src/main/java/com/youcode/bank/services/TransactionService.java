package com.youcode.bank.services;

import com.youcode.bank.exceptions.FileOperationException;
import com.youcode.bank.exceptions.InvalidAmountException;
import com.youcode.bank.model.BankAccount;
import com.youcode.bank.model.Transaction;
import com.youcode.bank.model.TransactionType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TransactionService {
    public static int transactionCounter = 1;

    private static final String STATEMENTS_DIR = "statements/";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public Transaction createTransaction(TransactionType type, double amount) {
        String transactionId = "T" + String.format("%04d", transactionCounter++);

        Transaction transaction = new Transaction(transactionId, type, amount);
        transaction.setTransactionDate(new Date());
        return transaction;
    }


    public boolean processDeposit(BankAccount account, double amount) {
        if (amount <= 0) {
            throw new InvalidAmountException(amount);
        }

        Transaction transaction = createTransaction(TransactionType.DEPOSIT, amount);
        account.setBalance(account.getBalance() + amount);

        account.getHistoryOfTransactions().add(transaction);
        saveTransactionToFile(account.getAccountId(), transaction);
        System.out.println("Deposit of " + amount + " succeeded");
        return true;
    }

    public boolean proceessWithdrawl(BankAccount account, double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException(amount);
        }

        if (account.getBalance() < amount) {
            throw new InvalidAmountException(amount);
        }

        Transaction transaction = createTransaction(TransactionType.WITHDRAWAL, amount);

        account.setBalance(account.getBalance() - amount);
        account.getHistoryOfTransactions().add(transaction);
        saveTransactionToFile(account.getAccountId(), transaction);
        System.out.println("Withdrawal succeeded");
        return true;
    }

    public boolean processTransfer(BankAccount fromAccount, BankAccount toAccount, double amount) {
        if (amount <= 0) {
            throw new InvalidAmountException(amount);
        }

        if (fromAccount.getBalance() < amount) {
            throw new InvalidAmountException(amount);
        }

        Transaction transaction = createTransaction(TransactionType.TRANSFER, amount);

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        fromAccount.getHistoryOfTransactions().add(transaction);
        toAccount.getHistoryOfTransactions().add(transaction);

        saveTransferToFile(fromAccount.getAccountId(), toAccount.getAccountId(), transaction);
        System.out.println("Transfer of " + amount + " completed successfully");
        return true;
    }

    public void displayTransactionHistory(BankAccount account) {
        List<Transaction> transactions = account.getHistoryOfTransactions();

        if (transactions.isEmpty()) {
            System.out.println("No transactions for this account");
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
                    DATE_FORMAT.format(t.getTransactionDate())
            );
        }
        System.out.println("--------------------------------");
    }

    private void createStatementDirectory() {
        File dir = new File(STATEMENTS_DIR);
        if(!dir.exists()) {
            dir.mkdirs();
        }
    }

    private void saveTransactionToFile(String accountId, Transaction transaction) {
        createStatementDirectory();
        String filename = STATEMENTS_DIR + "account_" + accountId + ".txt";

        try (FileWriter writer = new FileWriter(filename, true)) { // true = append mode

            File file = new File(filename);
            if (file.length() == 0) {
                writer.write("=== RELEVÉ BANCAIRE - Compte: " + accountId + " ===\n");
                writer.write("Date | Type | Amount | Source Account | Destination Account\n");
                writer.write("--------------------------------------------------------\n");
            }

            // Write transaction
            String line = String.format("%s | %s | %.2f€ | %s | %s\n",
                    DATE_FORMAT.format(transaction.getTransactionDate()),
                    transaction.getType(),
                    transaction.getAmount(),
                    (transaction.getType() == TransactionType.DEPOSIT) ? "null" : accountId,
                    (transaction.getType() == TransactionType.WITHDRAWAL) ? "null" : accountId
            );

            writer.write(line);

        } catch (IOException e) {
            throw new FileOperationException("Error writing on file: " + e.getMessage());
        }
    }

    private void saveTransferToFile(String fromAccountId, String toAccountId, Transaction transaction) {
        saveTransferTransactionToFile(fromAccountId, transaction, fromAccountId, toAccountId);

        saveTransferTransactionToFile(toAccountId, transaction, fromAccountId, toAccountId);
    }


    private void saveTransferTransactionToFile(String accountId, Transaction transaction, String sourceAccount, String destAccount) {
        String filename = STATEMENTS_DIR + "account_" + accountId + ".txt";

        try (FileWriter writer = new FileWriter(filename, true)) {
            File file = new File(filename);
            if (file.length() == 0) {
                writer.write("=== RELEVÉ BANCAIRE - Compte: " + accountId + " ===\n");
                writer.write("Date | Type | Amount | Source Account | Destination Account\n");
                writer.write("--------------------------------------------------------\n");
            }

            // Write transfer transaction
            String line = String.format("%s | %s | %.2f€ | %s | %s\n",
                    DATE_FORMAT.format(transaction.getTransactionDate()),
                    transaction.getType(),
                    transaction.getAmount(),
                    sourceAccount,
                    destAccount
            );

            writer.write(line);

        } catch (IOException e) {
            throw new FileOperationException("Error writing on file: " + e.getMessage());
        }
    }
}
