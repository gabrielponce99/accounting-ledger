package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Transaction> transactions = new ArrayList<>();

    public static void main(String[] args) {
        displayWelcomeBanner();
        loadTransactions();
        runHomeScreen();
    }

    public static void displayWelcomeBanner() {
        System.out.println("""
                     ___           ___                         ___           ___           ___           ___    \s
                    /\\  \\         /\\__\\                       /\\__\\         /\\  \\         /\\  \\         /\\__\\   \s
                   _\\:\\  \\       /:/ _/_                     /:/  /        /::\\  \\       |::\\  \\       /:/ _/_  \s
                  /\\ \\:\\  \\     /:/ /\\__\\                   /:/  /        /:/\\:\\  \\      |:|:\\  \\     /:/ /\\__\\ \s
                 _\\:\\ \\:\\  \\   /:/ /:/ _/_   ___     ___   /:/  /  ___   /:/  \\:\\  \\   __|:|\\:\\  \\   /:/ /:/ _/_\s
                /\\ \\:\\ \\:\\__\\ /:/_/:/ /\\__\\ /\\  \\   /\\__\\ /:/__/  /\\__\\ /:/__/ \\:\\__\\ /::::|_\\:\\__\\ /:/_/:/ /\\__\\
                \\:\\ \\:\\/:/  / \\:\\/:/ /:/  / \\:\\  \\ /:/  / \\:\\  \\ /:/  / \\:\\  \\ /:/  / \\:\\~~\\  \\/__/ \\:\\/:/ /:/  /
                 \\:\\ \\::/  /   \\::/_/:/  /   \\:\\  /:/  /   \\:\\  /:/  /   \\:\\  /:/  /   \\:\\  \\        \\::/_/:/  /\s
                  \\:\\/:/  /     \\:\\/:/  /     \\:\\/:/  /     \\:\\/:/  /     \\:\\/:/  /     \\:\\  \\        \\:\\/:/  / \s
                   \\::/  /       \\::/  /       \\::/  /       \\::/  /       \\::/  /       \\:\\__\\        \\::/  /  \s
                    \\/__/         \\/__/         \\/__/         \\/__/         \\/__/         \\/__/         \\/__/   \s""");
    }

    public static void loadTransactions() {

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
                transactions.add(transaction);
            }
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("There was an error reading the transactions. Check the location of the file.");
        }
    }

    public static void displayAll() {
        System.out.println("=== All Transactions ===");

        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
        pressEnterToContinue();
    }

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
                case "A" -> displayAll();
                case "D" -> displayDeposits();
                case "P" -> displayPayments();
                case "R" -> runReportsScreen();
                case "H" -> System.out.println("Back to Home");
                default -> System.out.println("That isn't a command. Try again.");
            }
        } while (!command.equals("H"));
    }

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
                case "1" -> displayMonthToDate();
                case "2" -> displayPreviousMonth();
                case "3" -> displayYearToDate();
                case "4" -> displayPreviousYear();
                case "5" -> searchByVendor();
                case "0" -> System.out.println("Back to Ledger");
                default -> System.out.println("That is not a command, try again.");
            }
        } while (!command.equals("0"));
    }

    public static void addDeposit() {
        String description = promptForString("Description: ");
        String vendor = promptForString("Vendor: ");
        double amount = promptForDouble("Amount: ");

        while (amount <= 0) {
            System.out.println("The number must be greater than 0.");
            amount = promptForDouble("Amount: ");
        }

        Transaction transaction = new Transaction(LocalDate.now(), LocalTime.now(), description, vendor, amount);
        saveTransaction(transaction);
        System.out.println("Deposit saved.");
        pressEnterToContinue();
    }

    public static void makePayment() {
        String description = promptForString("Description: ");
        String vendor = promptForString("Vendor: ");
        double amount = promptForDouble("Amount: ");

        while (amount <= 0) {
            System.out.println("The number must be greater than 0.");
            amount = promptForDouble("Enter the amount: ");
        }

        Transaction transaction = new Transaction(LocalDate.now(), LocalTime.now(), description, vendor, -amount);
        saveTransaction(transaction);
        System.out.println("Payment saved.");
        pressEnterToContinue();
    }

    public static void saveTransaction(Transaction transaction) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String date = transaction.getDate().toString();
        String time = transaction.getTime().format(timeFormatter);

        String line = date + "|" + time + "|" + transaction.getDescription() + "|" + transaction.getVendor() + "|" + String.format("%.2f", transaction.getAmount()) + "\n";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.csv", true))) {
            writer.write(line);
            transactions.add(transaction);

        } catch (IOException e) {
            System.out.println("Unable to save the transaction " + e.getMessage());
        }

    }

    public static void displayDeposits() {
        System.out.println("=== Deposits ===");

        for (Transaction transaction : transactions) {
            if (transaction.getAmount() > 0) {
                System.out.println(transaction);
            }
        }
        pressEnterToContinue();
    }

    public static void displayPayments() {
        System.out.println("=== Payments ===");

        for (Transaction transaction : transactions) {
            if (transaction.getAmount() < 0) {
                System.out.println(transaction);
            }
        }
        pressEnterToContinue();
    }

    public static void displayMonthToDate() {
        LocalDate today = LocalDate.now();
        boolean found = false;

        System.out.println("=== Month To Date ===");

        for (Transaction transaction : transactions) {
            LocalDate time = transaction.getDate();
            if (time.getMonth() == today.getMonth() && time.getYear() == today.getYear()) {
                System.out.println(transaction);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No transactions found.");
        }
        pressEnterToContinue();
    }

    public static void displayPreviousMonth() {
        LocalDate lastMonth = LocalDate.now().minusMonths(1);
        boolean found = false;

        System.out.println("=== Previous Year ===");

        for (Transaction transaction : transactions) {
            LocalDate time = transaction.getDate();
            if (time.getMonth() == lastMonth.getMonth() && time.getYear() == lastMonth.getYear()) {
                System.out.println(transaction);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No transactions found.");
        }
        pressEnterToContinue();
    }

    public static void displayYearToDate() {
        LocalDate today = LocalDate.now();
        boolean found = false;

        System.out.println("=== Year To Date ===");

        for (Transaction transaction : transactions) {
            LocalDate time = transaction.getDate();
            if (today.getYear() == time.getYear()) {
                System.out.println(transaction);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No transactions found.");
        }
        pressEnterToContinue();
    }

    public static void displayPreviousYear() {
        LocalDate lastYear = LocalDate.now().minusYears(1);
        boolean found = false;

        System.out.println("=== Previous Year ===");

        for (Transaction transaction : transactions) {
            LocalDate time = transaction.getDate();
            if (time.getYear() == lastYear.getYear()) {
                System.out.println(transaction);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No transactions found.");
        }
        pressEnterToContinue();
    }

    public static void searchByVendor() {
        boolean found = false;
        String vendor = promptForString("Enter the desired vendor: ");

        for (Transaction transaction : transactions) {
            if (transaction.getVendor().equalsIgnoreCase(vendor)) {
                System.out.println(transaction);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No transactions found.");
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

    // Input helper (String)
    private static String promptForString(String prompt) {
        while (true) {
            System.out.print(prompt);
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

    // Press Enter to Continue Action
    public static void pressEnterToContinue() {
        System.out.println();
        System.out.println("Press enter to continue back to the menu...");
        scanner.nextLine();
    }

}
