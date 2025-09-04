package com.stocktracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Collection;

/**
 * Unit tests for the PortfolioManager class.
 * These tests use a mock PriceFetcher to ensure predictable results.
 */
class PortfolioManagerTest {

    private PortfolioManager manager;
    private PriceFetcher mockPriceFetcher;

    /**
     * A mock PriceFetcher that returns predictable prices for testing.
     */
    private static class MockPriceFetcher extends PriceFetcher {
        @Override
        public double getCurrentPrice(String symbol, double buyPrice) {
            // Return fixed prices for testing purposes
            switch (symbol) {
                case "AAPL": return 150.0;
                case "GOOG": return 2800.0;
                default: return buyPrice; // Return buy price for any other symbol
            }
        }
    }

    @BeforeEach
    void setUp() {
        // This method runs before each test, ensuring a clean state.
        mockPriceFetcher = new MockPriceFetcher();
        manager = new PortfolioManager(mockPriceFetcher);
    }

    @Test
    void testAddStock() {
        manager.addStock("AAPL", 10, 130.0);
        Collection<Stock> portfolio = manager.getPortfolio();
        assertEquals(1, portfolio.size());
        Stock appleStock = portfolio.iterator().next();
        assertEquals("AAPL", appleStock.getSymbol());
        assertEquals(10, appleStock.getQuantity());
    }


    @Test
    void testAddExistingStockUpdatesQuantityAndAveragePrice() {
        manager.addStock("AAPL", 10, 100.0); // 10 shares @ $100
        manager.addStock("AAPL", 10, 120.0); // 10 more shares @ $120
        
        Collection<Stock> portfolio = manager.getPortfolio();
        assertEquals(1, portfolio.size());
        
        Stock appleStock = portfolio.iterator().next();
        assertEquals(20, appleStock.getQuantity());
        // Expected avg price: ((10*100) + (10*120)) / 20 = (1000 + 1200) / 20 = 2200 / 20 = 110.0
        assertEquals(110.0, appleStock.getBuyPrice(), 0.001);
    }

    @Test
    void testRemoveStock() {
        manager.addStock("AAPL", 10, 130.0);
        boolean removed = manager.removeStock("AAPL");
        assertTrue(removed);
        assertEquals(0, manager.getPortfolio().size());
    }
    
    @Test
    void testRemoveNonExistentStock() {
        boolean removed = manager.removeStock("MSFT");
        assertFalse(removed);
    }

    @Test
    void testCalculateTotalValue() {
        manager.addStock("AAPL", 10, 130.0); // Current price will be 150.0 from mock
        manager.addStock("GOOG", 2, 2700.0); // Current price will be 2800.0 from mock

        // Trigger price update by getting the portfolio
        manager.getPortfolio();
        
        // Expected total value = (10 * 150.0) + (2 * 2800.0) = 1500 + 5600 = 7100
        assertEquals(7100.0, manager.calculateTotalValue(), 0.001);
    }

    @Test
    void testGetPortfolioUpdatesPrices() {
        manager.addStock("AAPL", 10, 130.0);
        Collection<Stock> portfolio = manager.getPortfolio();
        Stock appleStock = portfolio.iterator().next();
        
        // The mock fetcher returns 150.0 for AAPL
        assertEquals(150.0, appleStock.getCurrentPrice(), 0.001);
    }
}