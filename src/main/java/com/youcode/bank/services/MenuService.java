package com.youcode.bank.services;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MenuService {
    private final Scanner scanner;

    public MenuService(Scanner scanner) {
        this.scanner = scanner;
    }

    public int displayMenuAndGetChoice(String title, List<MenuItem> items) {
        printHeader(title);
        for (MenuItem item : items) {
            System.out.printf("  %2d) %s%n", item.getValue(), item.getText());
        }
        System.out.println("-".repeat(40));
        return getIntInput("Choose an option");
    }

    public String getInputString(String prompt) {
        System.out.print(prompt + ": ");
        String line;
        if (scanner.hasNextLine()) {
            line = scanner.nextLine();
            if (line.isEmpty()) {
                line = scanner.nextLine();
            }
        } else {
            line = scanner.next();
        }
        return line.trim();
    }

    public double getDoubleInput(String prompt) {
        while (true) {
            System.out.print(prompt + ": ");
            String s = readLineSafe();
            try {
                return Double.parseDouble(s.trim());
            } catch (NumberFormatException e) {
                System.out.println("[!] Please enter a valid number.");
            }
        }
    }

    public int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt + ": ");
            String s = readLineSafe();
            try {
                return Integer.parseInt(s.trim());
            } catch (NumberFormatException e) {
                System.out.println("[!] Please enter a valid integer.");
            }
        }
    }

    private void printHeader(String title) {
        String line = "=".repeat(Math.max(30, title.length() + 10));
        System.out.println(line);
        System.out.printf("|| %s%n", title);
        System.out.println(line);
    }

    private String readLineSafe() {
        String line;
        if (scanner.hasNextLine()) {
            line = scanner.nextLine();
            if (line.isEmpty()) { // possible leftover newline
                line = scanner.nextLine();
            }
        } else {
            // fallback for environments where nextLine may not be available initially
            line = scanner.next();
        }
        return line;
    }
}
