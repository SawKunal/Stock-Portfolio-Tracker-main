package com.stocktracker;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Simulates fetching stock prices.
 * In a real application, this class would be replaced with one that calls a financial data API.
 * This implementation generates a random price fluctuation around the buy price.
 */
public class PriceFetcher {

    private final Random random = new Random();
    // Cache prices for a session to maintain consistency during a single view.
    private final Map<String, Double> priceCache = new HashMap<>();

    /**
     * Fetches the "current" price for a given stock symbol.
     * This simulates a price by taking the stock's buy price and applying a random
     * fluctuation (-10% to +10%). It caches the price for the session.
     *
     * @param symbol   The stock symbol.
     * @param buyPrice The original buy price, used as a base for simulation.
     * @return A simulated current price.
     */
    public double getCurrentPrice(String symbol, double buyPrice) {
        // If we already fetched the price in this session, return the cached value.
        if (priceCache.containsKey(symbol)) {
            return priceCache.get(symbol);
        }

        // Simulate a price fluctuation between -10% and +10% of the buy price
        double fluctuation = (random.nextDouble() * 0.2) - 0.1; // -0.1 to +0.1
        double simulatedPrice = buyPrice * (1 + fluctuation);

        // Ensure price is not negative
        double finalPrice = Math.max(0, simulatedPrice);
        
        priceCache.put(symbol, finalPrice);
        return finalPrice;
    }

    /**
     * Clears the cache of fetched prices. This should be called before viewing the portfolio
     * to get "fresh" data for the new view.
     */
    public void clearCache() {
        priceCache.clear();
    }
}