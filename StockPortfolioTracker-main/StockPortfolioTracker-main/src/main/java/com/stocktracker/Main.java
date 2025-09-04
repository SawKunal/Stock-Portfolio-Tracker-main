package com.stocktracker;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The main entry point for the Stock Portfolio Tracker application.
 * It provides a command-line interface for users to manage their portfolio.
 */
public class Main {

    private static final PortfolioManager manager = new PortfolioManager(new PriceFetcher());
    private static final ReportService reportService = new ReportService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to the Stock Portfolio Tracker!");
        boolean running = true;

        while (running) {
            printMenu();
            System.out.print("Choose an option: ");
            int choice = -1;
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear the invalid input
                continue;
            }
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addStock();
                    break;
                case 2:
                    removeStock();
                    break;
                case 3:
                    viewPortfolio();
                    break;
                case 4:
                    exportPortfolio();
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        System.out.println("Thank you for using the Stock Portfolio Tracker!");
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Add a stock to your portfolio");
        System.out.println("2. Remove a stock from your portfolio");
        System.out.println("3. View your portfolio");
        System.out.println("4. Export portfolio to CSV");
        System.out.println("5. Exit");
    }

    private static void addStock() {
        try {
            System.out.print("Enter stock symbol (e.g., AAPL): ");
            String symbol = scanner.nextLine().toUpperCase();
            System.out.print("Enter quantity: ");
            int quantity = scanner.nextInt();
            System.out.print("Enter buy price per share: ");
            double buyPrice = scanner.nextDouble();
            scanner.nextLine(); // Consume newline

            if (quantity <= 0 || buyPrice <= 0) {
                System.out.println("Quantity and buy price must be positive.");
                return;
            }

            manager.addStock(symbol, quantity, buyPrice);
            System.out.println("Stock " + symbol + " added successfully.");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter correct data types.");
            scanner.nextLine(); // Clear the invalid input
        }
    }

    private static void removeStock() {
        System.out.print("Enter stock symbol to remove: ");
        String symbol = scanner.nextLine().toUpperCase();
        if (manager.removeStock(symbol)) {
            System.out.println("Stock " + symbol + " removed successfully.");
        } else {
            System.out.println("Stock " + symbol + " not found in portfolio.");
        }
    }

    private static void viewPortfolio() {
        Collection<Stock> portfolio = manager.getPortfolio();
        if (portfolio.isEmpty()) {
            System.out.println("Your portfolio is empty.");
            return;
        }

        System.out.println("\n--------------------------------- Your Portfolio ---------------------------------");
        System.out.printf("%-10s %-10s %-15s %-15s %-15s %-15s%n",
                "Symbol", "Quantity", "Buy Price", "Current Price", "Total Value", "Gain/Loss");
        System.out.println("----------------------------------------------------------------------------------");

        double totalPortfolioValue = 0;
        double totalGainLoss = 0;

        for (Stock stock : portfolio) {
            double totalValue = stock.getTotalValue();
            double gainLoss = stock.getGainLoss();
            totalPortfolioValue += totalValue;
            totalGainLoss += gainLoss;
            
            System.out.printf("%-10s %-10d $%-14.2f $%-14.2f $%-14.2f $%-14.2f%n",
                    stock.getSymbol(),
                    stock.getQuantity(),
                    stock.getBuyPrice(),
                    stock.getCurrentPrice(),
                    totalValue,
                    gainLoss);
        }

        System.out.println("----------------------------------------------------------------------------------");
        System.out.printf("Total Portfolio Value: $%.2f%n", totalPortfolioValue);
        System.out.printf("Total Portfolio Gain/Loss: $%.2f%n", totalGainLoss);
        System.out.println("----------------------------------------------------------------------------------");
    }

    private static void exportPortfolio() {
        if (manager.getPortfolio().isEmpty()) {
            System.out.println("Portfolio is empty. Nothing to export.");
            return;
        }
        
        // Create a directory for reports if it doesn't exist
        File reportDir = new File("generated_reports");
        if (!reportDir.exists()) {
            reportDir.mkdir();
        }

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filePath = "generated_reports/portfolio_summary_" + timestamp + ".csv";
        reportService.exportToCSV(manager.getPortfolio(), filePath);
    }
}