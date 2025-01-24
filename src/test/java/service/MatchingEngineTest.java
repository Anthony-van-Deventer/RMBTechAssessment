package service;

import model.Side;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatchingEngineTest {

    private MatchingEngine matchingEngine;
    private OrderBook orderBook;

    @BeforeEach
    void setUp() {
        orderBook = new OrderBook();
        matchingEngine = new MatchingEngine(orderBook);
    }

    @Test
    void testFillOrderNoMatch() {
        matchingEngine.fillOrder(10.0, 100, Side.BUY);
        assertEquals(1, orderBook.getOrdersByPriceAndSide(10.0, Side.BUY).size());
    }

    @Test
    void testFillOrderFullMatch() {
        orderBook.addOrder(10.0, 100, Side.BUY);
        orderBook.addOrder(10.0, 100, Side.SELL);

        matchingEngine.fillOrder(10.0, 100, Side.BUY);

        assertTrue(orderBook.getOrdersByPriceAndSide(10.0, Side.SELL).isEmpty());
        assertFalse(orderBook.getOrdersByPriceAndSide(10.0, Side.BUY).isEmpty());
    }

    @Test
    void testFillOrderPartialMatch() {
        orderBook.addOrder(10.0, 100, Side.BUY);
        orderBook.addOrder(10.0, 150, Side.SELL);

        matchingEngine.fillOrder(10.0, 100, Side.BUY);

        assertEquals(50, orderBook.getOrdersByPriceAndSide(10.0, Side.SELL).get(0).getQuantity());
    }

    @Test
    void testFillOrderNoExistingOrders() {
        matchingEngine.fillOrder(15.0, 100, Side.BUY);
        assertEquals(1, orderBook.getOrdersByPriceAndSide(15.0, Side.BUY).size());
    }
}
