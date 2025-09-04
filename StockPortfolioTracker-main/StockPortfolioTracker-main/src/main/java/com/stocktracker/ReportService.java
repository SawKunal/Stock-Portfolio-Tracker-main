package com.stocktracker;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

/**
 * Service responsible for generating reports from portfolio data.
 */
public class ReportService {

    /**
     * Exports the current portfolio status to a CSV file.
     * The file will contain headers and a row for each stock.
     *
     * @param portfolio The collection of stocks to export.
     * @param filePath  The path where the CSV file will be saved.
     */
    public void exportToCSV(Collection<Stock> portfolio, String filePath) {
        // Use try-with-resources to ensure the writer is closed automatically
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            // Write CSV header
            writer.println("Symbol,Quantity,BuyPrice,CurrentPrice,TotalValue,GainLoss");

            // Write each stock as a row in the CSV
            for (Stock stock : portfolio) {
                writer.printf("%s,%d,%.2f,%.2f,%.2f,%.2f%n",
                        stock.getSymbol(),
                        stock.getQuantity(),
                        stock.getBuyPrice(),
                        stock.getCurrentPrice(),
                        stock.getTotalValue(),
                        stock.getGainLoss());
            }
            System.out.println("Portfolio successfully exported to " + filePath);
        } catch (IOException e) {
            System.err.println("Error exporting portfolio to CSV: " + e.getMessage());
        }
    }
}