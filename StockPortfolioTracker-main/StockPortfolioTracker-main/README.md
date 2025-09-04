# Stock Portfolio Tracker

This is a simple, beginner-friendly Java application for tracking a stock portfolio. It's built using Object-Oriented principles and provides a basic command-line interface (CLI).

## Features

- **Add a stock**: Add a new stock to your portfolio with its symbol, quantity, and buy price.
- **Remove a stock**: Remove a stock from your portfolio using its symbol.
- **View portfolio**: See a summary of all your stocks, including current value and gain/loss per stock.
- **Simulated Prices**: Stock prices are simulated with a random generator for demonstration purposes.
- **Export to CSV**: Export your portfolio summary to a `.csv` file.
- **Unit Tested**: Core business logic in `PortfolioManager` is verified with JUnit tests.

## Project Structure

The project is organized into several classes, each with a specific responsibility:

- `Main.java`: Handles all user interaction via the command-line interface.
- `Stock.java`: A model class representing a single stock holding.
- `PortfolioManager.java`: The core class that manages the list of stocks, and handles adding, removing, and calculations.
- `PriceFetcher.java`: A service class responsible for fetching stock prices. It currently simulates prices but is designed to be easily replaced with a real API.
- `ReportService.java`: A service for exporting portfolio data to different formats (currently CSV).
- `PortfolioManagerTest.java`: JUnit tests for the `PortfolioManager` class.

## How to Run

### Prerequisites

- Java Development Kit (JDK) 11 or higher.
- Apache Maven (for easy dependency management and running).
- Git (to clone the repository).

### Steps

1.  **The Project Directory:**
    All Java files will go into the `src/main/java/com/stocktracker` and `src/test/java/com/stocktracker` folders.

    ```
    StockPortfolioTracker/
    ├── src/
    │   ├── main/
    │   │   └── java/
    │   │       └── com/
    │   │           └── stocktracker/
    │   │               ├── (Java files go here)
    │   │
    │   └── test/
    │       └── java/
    │           └── com/
    │               └── stocktracker/
    │                   └── (Test files go here)
    │
    ├── pom.xml
    └── README.md
    ```

2.  **Compile and Run the Application:**
    Open a terminal or command prompt in the root `StockPortfolioTracker/` directory.

    - **Compile the code:**
      ```sh
      mvn compile
      ```
    - **Run the main application:**
      ```sh
      mvn exec:java
      ```

3.  **Run the Tests:**
    To ensure everything is working correctly, run the unit tests:
    ```sh
    mvn test
    ```

## Sample Output

Here is a sample session with the application:
Welcome to the Stock Portfolio Tracker!

--- Main Menu ---

Add a stock to your portfolio

Remove a stock from your portfolio

View your portfolio

Export portfolio to CSV

Exit
Choose an option: 1
Enter stock symbol (e.g., AAPL): AAPL
Enter quantity: 20
Enter buy price per share: 150.50
Stock AAPL added successfully.

--- Main Menu ---
...
Choose an option: 1
Enter stock symbol (e.g., AAPL): GOOG
Enter quantity: 5
Enter buy price per share: 2750
Stock GOOG added successfully.

--- Main Menu ---
...
Choose an option: 3

--------------------------------- Your Portfolio ---------------------------------
Symbol Quantity Buy Price Current Price Total Value Gain/Loss
AAPL 20 $150.50 $141.65 $2833.02 $-176.98
GOOG 5 $2750.00 $2912.87 $14564.35 $814.35
Total Portfolio Value: $17397.37
Total Portfolio Gain/Loss: $637.37
--- Main Menu ---
...
Choose an option: 4
Portfolio successfully exported to generated_reports/portfolio_summary_20231027_103000.csv

--- Main Menu ---
...
Choose an option: 5
Thank you for using the Stock Portfolio Tracker!

---
