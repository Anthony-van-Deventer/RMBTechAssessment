package service;

import model.Order;
import model.Side;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderBookTest {

    private OrderBook orderBook;

    @BeforeEach
    void setUp() {
        orderBook = new OrderBook();
        orderBook.addOrder(100.5, 20, Side.BUY);
        orderBook.addOrder(100.5, 1000,Side.BUY);
        orderBook.addOrder(100.5, 300,Side.BUY);
        orderBook.addOrder(100.5, 20,Side.BUY);
        orderBook.addOrder(100.5, 9,Side.BUY);

        orderBook.addOrder(100.5, 1, Side.SELL);
        orderBook.addOrder(100.5, 2, Side.SELL);
        orderBook.addOrder(100.5, 3, Side.SELL);
        orderBook.addOrder(100.5, 4, Side.SELL);
        orderBook.addOrder(100.5, 5, Side.SELL);
    }

    @Test
    void testAddOrder() {
        Order addedOrder = orderBook.addOrder(100.5, 50, Side.BUY);
        Order order = orderBook.getOrder(addedOrder.getId());

        assertNotNull(order, "Order was not deleted.");
        assertEquals(100.5, order.getPrice(), "Incorrect Price for added order.");
        assertEquals(50, order.getQuantity(), "Incorrect Quantity for added order.");
        assertEquals(Side.BUY, order.getSide(), "Incorrect Side for added order.");
    }

    @Test
    void testAddMultipleOrdersAddedByFIFO() {
        //check the buy sides that FIFO is implemented correctly
        List<Order> buyList = orderBook.getOrdersByPriceAndSide(100.5, Side.BUY);
        assertEquals(buyList.get(0).getQuantity(), 20);
        assertEquals(buyList.get(1).getQuantity(), 1000);
        assertEquals(buyList.get(2).getQuantity(), 300);
        assertEquals(buyList.get(3).getQuantity(), 20);
        assertEquals(buyList.get(4).getQuantity(), 9);

        //check the buy sides that FIFO is implemented correctly
        List<Order> askList = orderBook.getOrdersByPriceAndSide(100.5, Side.SELL);
        assertEquals(askList.get(0).getQuantity(), 1);
        assertEquals(askList.get(1).getQuantity(), 2);
        assertEquals(askList.get(2).getQuantity(), 3);
        assertEquals(askList.get(3).getQuantity(), 4);
        assertEquals(askList.get(4).getQuantity(), 5);
        OrderBookPrinter.printOrderBook(orderBook);

    }

    @Test
    void testDeleteOrder() {
        // Add and delete a SELL order
        Order order = orderBook.addOrder(101.0, 100, Side.SELL);
        Order deletedOrder = orderBook.deleteOrder(order.getId());

        assertNotNull(deletedOrder, "Order was not deleted");
        assertEquals(101.0, deletedOrder.getPrice(), "Incorrect price for deleted order");
        assertEquals(100, deletedOrder.getQuantity(), "Incorrect quantity for deleted order");
        assertEquals(Side.SELL, deletedOrder.getSide(), "Incorrect side for deleted order");
        assertNull(orderBook.getOrder(order.getId()), "Order still exists after deletion");
    }

    @Test
    void testModifyOrder() {
        // Add a BUY order and modify it
        Order addedOrder = orderBook.addOrder(102.5, 30, Side.BUY);
        Order modifiedOrder = orderBook.modifyOrder(addedOrder.getId(), 60);

        Order updatedModifier = orderBook.getOrder(modifiedOrder.getId()); // Modified order gets a new ID

        assertNotNull(updatedModifier, "Order was not modified");
        assertEquals(102.5, updatedModifier.getPrice(), "Incorrect price for modified order");
        assertEquals(60, updatedModifier.getQuantity(), "Incorrect quantity for modified order");
        assertEquals(Side.BUY, updatedModifier.getSide(), "Incorrect side for modified order");
        assertNotEquals(orderBook.getOrder(0),orderBook.getOrder(updatedModifier.getId()), "Old order still exists after modification");
    }

    @Test
    void testGetOrder() {
        // Add a BUY order and retrieve it
        Order addedOrder = orderBook.addOrder(103.0, 20, Side.BUY);
        Order retrievedOrder = orderBook.getOrder(addedOrder.getId());

        assertNotNull(retrievedOrder, "Order was not retrieved");
        assertEquals(103.0, retrievedOrder.getPrice(), "Incorrect price for retrieved order");
        assertEquals(20, retrievedOrder.getQuantity(), "Incorrect quantity for retrieved order");
        assertEquals(Side.BUY, retrievedOrder.getSide(), "Incorrect side for retrieved order");
    }
}
