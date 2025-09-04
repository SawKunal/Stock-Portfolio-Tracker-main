package com.stocktracker;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages the collection of stocks in the user's portfolio.
 * Handles adding, removing, and updating stocks, as well as calculating portfolio metrics.
 */
public class PortfolioManager {

    // Using a HashMap for efficient lookup by stock symbol.
    private final Map<String, Stock> portfolio;
    private final PriceFetcher priceFetcher;

    /**
     * Constructs a PortfolioManager with a dependency on a PriceFetcher.
     * @param priceFetcher The service used to get current stock prices.
     */
    public PortfolioManager(PriceFetcher priceFetcher) {
        this.portfolio = new HashMap<>();
        this.priceFetcher = priceFetcher;
    }

    /**
     * Adds a stock to the portfolio. If the stock already exists, it updates the
     * quantity and recalculates the average buy price.
     *
     * @param symbol   The stock's symbol.
     * @param quantity The number of shares to add.
     * @param buyPrice The price at which these new shares were bought.
     */
    public void addStock(String symbol, int quantity, double buyPrice) {
        if (portfolio.containsKey(symbol)) {
            // Stock already exists, update it
            Stock existingStock = portfolio.get(symbol);
            int totalQuantity = existingStock.getQuantity() + quantity;
            double totalCost = (existingStock.getQuantity() * existingStock.getBuyPrice()) + (quantity * buyPrice);
            double newAverageBuyPrice = totalCost / totalQuantity;

            existingStock.setQuantity(totalQuantity);
            existingStock.setBuyPrice(newAverageBuyPrice);
        } else {
            // New stock, add it to the portfolio
            Stock newStock = new Stock(symbol, quantity, buyPrice);
            portfolio.put(symbol, newStock);
        }
    }

    /**
     * Removes a stock from the portfolio.
     * @param symbol The symbol of the stock to remove.
     * @return true if the stock was removed, false otherwise.
     */
    public boolean removeStock(String symbol) {
        return portfolio.remove(symbol) != null;
    }
    
    /**
     * Retrieves a read-only view of the portfolio.
     * This method first updates the current prices of all stocks.
     * @return A collection of all stocks in the portfolio.
     */
    public Collection<Stock> getPortfolio() {
        // Before viewing, update all current prices
        updateAllStockPrices();
        return Collections.unmodifiableCollection(portfolio.values());
    }

    /**
     * Updates the current market price for all stocks in the portfolio.
     */
    private void updateAllStockPrices() {
        priceFetcher.clearCache(); // Clear old prices for a fresh view
        for (Stock stock : portfolio.values()) {
            double currentPrice = priceFetcher.getCurrentPrice(stock.getSymbol(), stock.getBuyPrice());
            stock.setCurrentPrice(currentPrice);
        }
    }

    /**
     * Calculates the total current value of the entire portfolio.
     * @return The sum of the total value of each stock holding.
     */
    public double calculateTotalValue() {
        return portfolio.values().stream()
                .mapToDouble(Stock::getTotalValue)
                .sum();
    }
}