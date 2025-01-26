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
    void whenPartiallyFillingTheOrder_shouldUpdatePartiallyFilledOrderToQuantityMinusFilledQuantity() {
        orderBook.addOrder(10.0, 100, Side.BUY);
        orderBook.addOrder(10.0, 100, Side.SELL);

        matchingEngine.fillOrder(10.0, 50, Side.BUY);

        assertEquals(50,orderBook.getOrdersByPriceAndSide(10.0, Side.SELL).get(0).getQuantity());

    }

    @Test
    void whenFillingAnOrder_ShouldFillOrderCompletelyAndRemoveFilledOrder() {
        orderBook.addOrder(10.0, 100, Side.BUY);
        orderBook.addOrder(10.0, 150, Side.BUY);

        matchingEngine.fillOrder(10.0, 100, Side.SELL);

        assertEquals(150, orderBook.getOrdersByPriceAndSide(10.0, Side.BUY).get(0).getQuantity());
        assertEquals(1, orderBook.getOrdersByPriceAndSide(10.0, Side.BUY).size());
    }

    @Test
    void whenNoOrdersAtMatchingPrice_shouldAddThatOrder() {
        matchingEngine.fillOrder(1234.01, 100, Side.BUY);
        assertEquals(1, orderBook.getOrdersByPriceAndSide(1234.01, Side.BUY).size());
    }

    @Test
    void whenQuantityLeftOverAfterFillingOrder_shouldAddLeftOverAsNewOrderToOrderBook() {
        orderBook.addOrder(10.0, 100, Side.BUY);

        matchingEngine.fillOrder(10.0, 105, Side.SELL);
        assertEquals(5, orderBook.getOrdersByPriceAndSide(10.0, Side.SELL).get(0).getQuantity());
        assertEquals(1, orderBook.getOrdersByPriceAndSide(10.0, Side.SELL).size());
    }
}
