# Simplistic Java Order Book Framework

## Overview
This project implements a simplistic **Order Book** framework using **Java SE**. It provides functionality to manage and match orders in a trading system without relying on external frameworks, databases, or open-source libraries.

The project consists of the following components:
1. **OrderBook**: Handles storage and management of orders.
2. **MatchingEngine**: Matches buy and sell orders and updates the order book accordingly.
3. **Order Model**: Represents an individual order.
4. **Side Enum**: Represents the side of an order (BUY or SELL).

The solution prioritizes efficiency and simplicity while adhering to the constraints provided in the assessment.

---

## Project Requirements
1. **Strictly JAVA SE Application**
    - No SpringBoot or other frameworks.
    - No database.
    - No front-end.
2. **No open-source libraries**: Only standard Java libraries are used.
3. **Efficiency and Performance**: The code is designed for optimal efficiency in matching and order management.
4. **Test Cases**: Comprehensive unit tests are included.

---

## Efficiency Mechanisms
### 1. **Data Structures**
- **LinkedHashMap**:
    - Used to store orders grouped by price levels.
    - Maintains insertion order for easy traversal, ensuring that earlier orders at a given price level get matched first (FIFO).

- **LinkedList**:
    - Stores individual orders at each price level.
    - Provides efficient removal of orders after matching and fast traversal.

- **ArrayList**:
    - Temporarily holds IDs of orders to be removed during matching operations.

### 2. **Reasoning for Choices**
- **LinkedHashMap** allows efficient lookups and ensures predictable iteration order, which is crucial for prioritizing earlier orders.
- **LinkedList** is used at price levels because it supports fast insertion and removal, which are frequent operations in an order book.
- The design minimizes the need for unnecessary data copying or complex algorithms, keeping operations efficient.

---

## Solution Approach
1. **OrderBook Management**:
    - Orders are added, deleted, or modified in the order book using efficient data structures.
    - Orders are grouped by price and side (BUY or SELL) to simplify matching.
2. **Matching Engine**:
    - Matches incoming orders with existing orders in the order book.
    - Partial and full matches are handled seamlessly.
    - Updates the order book after matching.
3. **Test Cases**:
    - JUnit 5 tests ensure that the implementation handles various scenarios such as full matches, partial matches, and edge cases like empty books.

---

## Class Descriptions

### 1. **Order**
Represents an individual order with attributes:
- `id`: Unique identifier.
- `price`: Price of the order.
- `quantity`: Quantity of the order.
- `side`: Side of the order (BUY or SELL).

### 2. **Side**
An enum representing the side of an order:
- `BUY`: Represents buy orders.
- `SELL`: Represents sell orders.

### 3. **OrderBook**
Handles storage and management of orders:
- **Operations**:
    - Add orders.
    - Delete orders.
    - Modify orders.
    - Retrieve orders by price and side.
    - Support FIFO for matching within a price level.

### 4. **MatchingEngine**
Matches buy and sell orders:
- Processes incoming orders to match them with existing orders.
- Handles partial fills and updates remaining quantities.
- Adds unmatched orders to the order book.

---

## Testing
The project includes comprehensive **JUnit 5** test cases:
1. **OrderBookTests**:
    - Test adding, deleting, and modifying orders.
    - Verify order retrieval by price and side.
2. **MatchingEngineTests**:
    - Test full and partial order matching.
    - Handle scenarios where no matches are found.
    - Verify correct updates to the order book.

To run tests:
- Use an IDE (e.g., IntelliJ, Eclipse).
- Run `mvn test` for Maven or `gradle test` for Gradle if applicable.

---

## How to Run the Project
1. Clone the repository:
    ```bash
    git clone <repository-url>
    ```
2. Compile the project using your preferred IDE or `javac`.
3. Run test cases to verify the functionality.

---

## Why This Approach?
### Efficiency:
- By using **LinkedHashMap** and **LinkedList**, we achieve a balance between fast lookups and predictable iteration order for FIFO matching.

### Simplicity:
- The design avoids unnecessary complexity, making it easy to extend and maintain.

### Performance:
- The use of appropriate data structures ensures that operations like adding, deleting, and matching orders are efficient.

---

## Future Improvements
1. **Improved Matching Algorithm**:
    - Extend the matching engine to handle multiple price levels for more realistic trading scenarios.
2. **Concurrency Support**:
    - Add thread-safe mechanisms to support concurrent operations.
3. **Performance Metrics**:
    - Include logging or metrics to analyze performance under different workloads.

---

## Author
Anthony van Deventer

---

Feel free to reach out for further questions or clarifications!

