package com.youcode.bank;

import com.youcode.bank.console.ConsoleUI;
import com.youcode.bank.services.BankAccountService;
import com.youcode.bank.services.ClientService;
import com.youcode.bank.services.ManagerService;
import com.youcode.bank.services.TransactionService;

public class Main {
    public static void main(String[] args) {
        ClientService clientService = new ClientService();
        ManagerService managerService = new ManagerService();
        BankAccountService bankAccountService = new BankAccountService();
        TransactionService transactionService = new TransactionService();

        ConsoleUI ui = new ConsoleUI(clientService, managerService, bankAccountService, transactionService);
        ui.start();
    }
}
