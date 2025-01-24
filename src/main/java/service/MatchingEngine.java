package service;

import model.Order;
import model.Side;

import java.util.ArrayList;
import java.util.List;

public class MatchingEngine {

    private final OrderBook orderBook;

    public MatchingEngine() {
        this.orderBook = new OrderBook();
    }

    public void fillOrder(double price, int quantity, Side side) {
        Side sideToFill = side == Side.BUY ? Side.SELL : Side.BUY;
        List<Integer> idsToRemove = new ArrayList<>();
        List<Order> existingOrders = orderBook.getOrdersByPriceAndSide(price, sideToFill);
        if (existingOrders.isEmpty()) {
            orderBook.addOrder(price, quantity, side);
        } else {
            for (Order orderToCheck : existingOrders) {
                if (quantity >= orderToCheck.getQuantity()){
                    quantity -= orderToCheck.getQuantity();
                    idsToRemove.add(orderToCheck.getId());
                } else {
                    int modifiedQty = orderToCheck.getQuantity() - quantity;
                    orderToCheck.setQuantity(modifiedQty);
                }
            }
            //remove ids
            orderBook.deleteMultipleOrders(idsToRemove);
        }

    }
}
