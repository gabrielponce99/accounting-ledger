package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
        static Scanner scanner = new Scanner(System.in);
        static ArrayList<Transaction> statements = new ArrayList<>();


    public static void main(String[] args) {
        runHomeScreen();
    }

    // File Reader
    public static ArrayList<Transaction> loadTransactions() {
        ArrayList<Transaction> statements = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader("transactions.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            bufferedReader.readLine();

            while ((line = bufferedReader.readLine()) != null) {
                String[] fields = line.split("\\|");

                LocalDate date = LocalDate.parse(fields[0]);
                LocalTime time = LocalTime.parse(fields[1]);
                String description = fields[2];
                String vendor = fields[3];
                double amount = Double.parseDouble(fields[4]);
                Transaction transaction = new Transaction(date, time, description, vendor, amount);
                statements.add(transaction);

            }
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("There was an error reading the product file. Check the location of the file.");
        }
        return statements;
    }

    // toString to print File
    public static void printTransactions(ArrayList<Transaction> statements) {
        for (Transaction statement : statements) {
            System.out.println(statement);
        }
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
                case "P" -> makePayment();
                case "L" -> runLedgerScreen();
                case "X" -> System.out.println("Goodbye!");
                default -> System.out.println("That isn't a command. Try again.");
            }
        } while (!command.equals("X"));
    }

    // Ledger Screen
    public static void runLedgerScreen() {
        String command;
        do {
            System.out.println();
            System.out.println("=== Ledger ===");
            System.out.println("A) All");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");
            System.out.print("Enter a command: ");
            command = scanner.nextLine().trim().toUpperCase();

            switch (command) {
                case "A" -> printTransactions(loadTransactions());
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
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
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
                case "0" -> System.out.println("Back to Ledger");
                default -> System.out.println("That isn't a command. Try again.");
            }
        } while (!command.equals("0"));
    }

    // Action Deposit
    public static void addDeposit() {
        String description = promptForString("Description: ");
        String vendor = promptForString("Vendor: ");
        double amount = promptForDouble("Amount: ");

        while (amount <= 0) {
            System.out.println("The number must be greater than 0.");
            amount = promptForDouble("Amount: ");
        }

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String date = LocalDate.now().toString();
        String time = LocalTime.now().format(timeFormatter);

        String line = date + "|" + time + "|" + description + "|" + vendor + "|" + amount;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.csv", true))){
            writer.write(line);
            writer.newLine();
            System.out.println("Deposit saved.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }

        pressEnterToContinue();
    }

    // Make Payment Action
    public static void makePayment() {
        String description = promptForString("Description: ");
        String vendor = promptForString("Vendor: ");
        double amount = promptForDouble("Amount: ");

        while (amount <= 0) {
            System.out.println("The number must be greater than 0.");
            amount = promptForDouble("Amount: ");
        }

        amount = -amount;

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String date = LocalDate.now().toString();
        String time = LocalTime.now().format(timeFormatter);

        String line = date + "|" + time + "|" + description + "|" + vendor + "|" + amount;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.csv", true))){
            writer.write(line);
            writer.newLine();
            System.out.println("Purchased completed.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }

        pressEnterToContinue();
    }

    // Input helper (double)
    private static double promptForDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number, like 89.50.");
            }
        }
    }

    // Input helpers (String)
    private static String promptForString(String prompt) {
        while (true) {
            System.out.println(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("This can't be blank.");
            } else if (input.contains("|")) {
                System.out.println("Please don't use the | character.");
            } else {
                return input;
            }
        }
    }

    // Press Enter to Continue
    public static void pressEnterToContinue() {
        System.out.println();
        System.out.println("Press enter to continue back to the menu...");
        scanner.nextLine();
    }

}
