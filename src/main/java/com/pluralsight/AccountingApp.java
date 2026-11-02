package com.pluralsight;

import java.util.ArrayList;
import java.util.Scanner;

public class AccountingApp {
        static Scanner scanner = new Scanner(System.in);
        static ArrayList<Transaction> transactions = new ArrayList<>();
    public static void main(String[] args) {
        runHomeScreen();
    }

    // Main Home Screen
    public static void runHomeScreen() {
        String command;
        do {
            System.out.println();
            System.out.println("=== Home ===");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");
            System.out.print("Enter a command: ");
            command = scanner.nextLine().trim().toUpperCase();

            switch (command) {
                case "D" -> addDeposit();
                case "P" -> System.out.println(" ");
                case "L" -> runScreenLedger();
                case "X" -> System.out.println("Goodbye!");
                default -> System.out.println("That isn't a command. Try again.");
            }
        } while (!command.equals("X"));
    }

    // Ledger Screen
    public static void runScreenLedger() {
        String command;
        do {
            System.out.println();
            System.out.println("=== Ledger ===");
            System.out.println("A) All");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Back to homescreen");
            System.out.print("Enter a command: ");
            command = scanner.nextLine().trim().toUpperCase();

            switch (command) {
                case "A" -> System.out.println(" ");
                case "D" -> System.out.println(" ");
                case "P" -> System.out.println(" ");
                case "R" -> runReportsScreen();
                case "H" -> System.out.println("Back to Home");
                default -> System.out.println("That isn't a command. Try again.");
            }
        } while (!command.equals("H"));
    }

    // Reports Screen
    public static void runReportsScreen() {
        String command;
        do {
            System.out.println();
            System.out.println("=== Reports ===");
            System.out.println("1) Month to Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year to Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back");
            System.out.print("Enter a command: ");
            command = scanner.nextLine().trim().toUpperCase();

            switch (command) {
                case "1" -> System.out.println(" ");
                case "2" -> System.out.println(" ");
                case "3" -> System.out.println(" ");
                case "4" -> System.out.println(" ");
                case "5" -> System.out.println(" ");
                case "0" -> System.out.println("Back to Browse Movies");
                default -> System.out.println("That isn't a command. Try again.");
            }
        } while (!command.equals("0"));
    }

    public static void addDeposit() {
        System.out.println("All the deposits");
        pressEnterToContinue();
    }

    public static void pressEnterToContinue() {
        System.out.println();
        System.out.println("Press enter to continue back to the menu...");
        scanner.nextLine();
    }

}
