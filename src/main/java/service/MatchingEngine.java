package service;

import model.Order;
import model.Side;

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
        //looking to fill orders of the order side
        Side sideToFill = side == Side.BUY ? Side.SELL : Side.BUY;
        List<Order> existingOrders = orderBook.getOrdersByPriceAndSide(price, sideToFill);
        if (existingOrders.isEmpty()) {
            orderBook.addOrder(price, quantity, side);
        } else {
            //using an iterator to be able to remove from the collection while iterating over it
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
                    //quantity is smaller than the quantity of the matching order, so we can leave the function
                    return;
                }
            }
            //if after filling the orders the new order still has quantity left then its added to the orderbook
            if (quantity>0) orderBook.addOrder(price, quantity, side);
        }

    }
}
