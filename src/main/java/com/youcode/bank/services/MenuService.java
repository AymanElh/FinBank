package com.youcode.bank.services;

import java.util.List;
import java.util.Scanner;

public class MenuService {
    private Scanner scanner;

    public MenuService(Scanner scanner) {
        this.scanner = scanner;
    }

    public int displayMenuAndGetChoice(String title, List<MenuItem> items) {
        System.out.println("=".repeat(20));
        System.out.println("  -  " + title + "  -  ");

        for(MenuItem item: items) {
            System.out.println(item.getValue() + ". " + item.getText());
        }

        return scanner.nextInt();
    }

    public String getInputString(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.next();
    }

    public double getDoubleInput(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextDouble();
    }

}
