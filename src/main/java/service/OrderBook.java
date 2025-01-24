package service;

import model.Order;
import model.Side;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class OrderBook {

    private final Map<Double, LinkedList<Order>> askOrders = new LinkedHashMap<>();
    private final Map<Double, LinkedList<Order>> sellOrders = new LinkedHashMap<>();
    private final Map<Integer, Order> orderMap = new LinkedHashMap<>();
    private int orderIdCount = 0;

    public Order addOrder(double price, int quantity, Side side) {
        final Order orderToAdd = new Order(orderIdCount, price, quantity, side);
        Map<Double, LinkedList<Order>> ordersToUse = getOrderMapBasedOnSide(orderToAdd.getSide());
        orderMap.put(orderIdCount, orderToAdd);
        orderIdCount++;
        ordersToUse.computeIfAbsent(orderToAdd.getPrice(), list -> new LinkedList<>()).add(orderToAdd);
        return orderToAdd;
    }

    public Order deleteOrder(Integer orderId) {
        Order orderToRemove = orderMap.remove(orderId);
        Map<Double, LinkedList<Order>> ordersToUse = getOrderMapBasedOnSide(orderToRemove.getSide());
        List<Order> ordersAtPrice = ordersToUse.get(orderToRemove.getPrice());
        ordersAtPrice.remove(orderToRemove);
        if (ordersAtPrice.isEmpty()) ordersToUse.remove(orderToRemove.getPrice());
        return orderToRemove;
    }

    public Order modifyOrder(Integer orderId, int quantity) {
        Order orderToModify = deleteOrder(orderId);
        return addOrder(orderToModify.getPrice(), quantity, orderToModify.getSide());
    }

    public Order getOrder(Integer orderId){
        return orderMap.get(orderId);
    }

    public List<Order> getOrdersByPriceAndSide(double price, Side side){
        return getOrderMapBasedOnSide(side).get(price);
    }

    public void deleteMultipleOrders(List<Integer> idsToDelete){
        idsToDelete.forEach(this::deleteOrder);
    }

    private Map<Double, LinkedList<Order>> getOrderMapBasedOnSide(Side side) {
        return side == Side.BUY ? askOrders : sellOrders;
    }
}
