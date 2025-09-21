package com.youcode.bank.console;

import com.youcode.bank.model.Client;
import com.youcode.bank.model.Manager;
import com.youcode.bank.services.*;
import com.youcode.bank.services.MenuItem;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ManagerMenu {
    private final Manager manager;
    private final ManagerService managerService;
    private final ClientService clientService;
    private final BankAccountService bankAccountService;
    private final MenuService menuService;

    private final List<MenuItem> MANAGER_MENU  = Arrays.asList(
            new MenuItem(1, "Create new client"),
            new MenuItem(2, "View client accounts"),
            new MenuItem(0, "Logout")
    );

    public ManagerMenu(Scanner sc, Manager manager, ManagerService managerService, ClientService clientService, BankAccountService bankAccountService) {
        this.manager = manager;
        this.managerService = managerService;
        this.clientService = clientService;
        this.bankAccountService = bankAccountService;
        this.menuService = new MenuService(sc);
    }

    public void show() {
        boolean running = true;
        while (running) {
            int choice = menuService.displayMenuAndGetChoice("FinBank - Manager Menu", MANAGER_MENU);

            switch (choice) {
                case 1:
                    createNewClient();
                    break;

                case 2:
                    //

                case 0:
                    System.out.println("logging out...");
                    running = false;
                    break;
            }
        }
    }

    private Client createNewClient() {
        String name = menuService.getInputString("Name");
        String email = menuService.getInputString("Email");
        String password = menuService.getInputString("Password");

        Client client = new Client(name, email, password);
        clientService.addNewClient(client);
        return client;
    }
}
