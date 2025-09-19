package com.youcode.bank.console;

import com.youcode.bank.model.Client;
import com.youcode.bank.model.Manager;
import com.youcode.bank.services.*;
import com.youcode.bank.services.MenuItem;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ConsoleUI {
    private static final Scanner sc = new Scanner(System.in);
    private final ClientService clientService;
    private final ManagerService managerService;
    private final BankAccountService bankAccountService;
    private final TransactionService transactionService;
    private final MenuService menuService;

    private final List<MenuItem> MAIN_MENU = Arrays.asList(
            new MenuItem(1, "Client login"),
            new MenuItem(2, "Manager Login"),
            new MenuItem(3, "Exit")
    );

    public ConsoleUI(ClientService clientService, ManagerService managerService, BankAccountService bankAccountService, TransactionService transactionService) {
        this.clientService = clientService;
        this.managerService = managerService;
        this.bankAccountService = bankAccountService;
        this.transactionService = transactionService;
        this.menuService = new MenuService(sc);
        managerService.initializeManagerList();
    }

    public void start() {
        while (true) {
            int choice = menuService.displayMenuAndGetChoice("Enter your choice: ", MAIN_MENU);

            switch (choice) {
                case 1:
                    login("client");
                    break;

                case 2:
                    login("manager");
                    break;

                case 3:
                    System.out.println("Goodbye!");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void login(String role) {
        String email = menuService.getInputString("Enter your email ");
        String password = menuService.getInputString("Enter your password ");

        if (role.equals("client")) {
            Optional<Client> client = clientService.login(email, password);
            if(client.isPresent()) {
                System.out.println("Client logged in successfully");
                // Show client menu
                ClientMenu clientMenu = new ClientMenu(sc, client.get(), clientService, bankAccountService, transactionService);
                clientMenu.show();
            } else {
                System.out.println("Error: Invalid email or password");
            }
        } else if(role.equals("manager")) {
            Optional<Manager> manager = managerService.login(email, password);
            if(manager.isPresent()) {
                System.out.println("Manager logged in successfully");
                // Show manager menu
                ManagerMenu managerMenu = new ManagerMenu(sc, manager.get(), managerService, clientService, bankAccountService);
                managerMenu.show();
            } else {
                System.out.println("Error: Invalid email or password");
            }
        }
    }
}
