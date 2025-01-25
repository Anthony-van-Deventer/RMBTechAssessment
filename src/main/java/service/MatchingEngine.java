package service;

import model.Order;
import model.Side;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MatchingEngine {

    private final OrderBook orderBook;

    public MatchingEngine() {
        this.orderBook = new OrderBook();
    }

    public MatchingEngine(OrderBook orderBook) {
        this.orderBook = orderBook;
    }

    public void fillOrder(double price, int quantity, Side side) {
        Side sideToFill = side == Side.BUY ? Side.SELL : Side.BUY;
        List<Order> existingOrders = orderBook.getOrdersByPriceAndSide(price, sideToFill);
        if (existingOrders == null) {
            orderBook.addOrder(price, quantity, side);
        } else {
            Iterator<Order> orderListIterator = existingOrders.iterator();
            while (orderListIterator.hasNext()){
                Order orderToCheck = orderListIterator.next();
                if (quantity >= orderToCheck.getQuantity()) {
                    quantity -= orderToCheck.getQuantity();
                    orderListIterator.remove();
                }
                else {
                    int modifiedQty = orderToCheck.getQuantity() - quantity;
                    orderToCheck.setQuantity(modifiedQty);
                }
            }
            if (quantity>0) orderBook.addOrder(price, quantity, side);
        }

    }
}
