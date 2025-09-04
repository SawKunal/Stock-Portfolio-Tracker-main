package com.stocktracker;

/**
 * Represents a single stock in the portfolio.
 * This class holds information about the stock's symbol, the quantity owned,
 * the price at which it was purchased, and its current market price.
 */
public class Stock {
    private String symbol;
    private int quantity;
    private double buyPrice;
    private double currentPrice;

    /**
     * Constructs a new Stock object.
     *
     * @param symbol   The stock ticker symbol (e.g., "AAPL").
     * @param quantity The number of shares owned.
     * @param buyPrice The average price paid per share.
     */
    public Stock(String symbol, int quantity, double buyPrice) {
        this.symbol = symbol;
        this.quantity = quantity;
        this.buyPrice = buyPrice;
        this.currentPrice = buyPrice; // Initially, current price is the buy price.
    }

    // --- Getters and Setters ---

    public String getSymbol() {
        return symbol;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getBuyPrice() {
        return buyPrice;
    }
    
    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    /**
     * Calculates the total value of this stock holding.
     * @return The current value (quantity * currentPrice).
     */
    public double getTotalValue() {
        return this.quantity * this.currentPrice;
    }

    /**
     * Calculates the total gain or loss for this stock holding.
     * @return The difference between the current value and the initial cost.
     */
    public double getGainLoss() {
        return (this.currentPrice - this.buyPrice) * this.quantity;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "symbol='" + symbol + '\'' +
                ", quantity=" + quantity +
                ", buyPrice=" + buyPrice +
                ", currentPrice=" + currentPrice +
                '}';
    }
}