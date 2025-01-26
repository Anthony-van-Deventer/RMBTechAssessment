package service;

import model.Order;

import java.util.*;

public class OrderBookPrinter {

    public static void printOrderBook(OrderBook orderBook) {
        Map<Double, LinkedList<Order>> bidOrders = orderBook.getBidOrders();
        Map<Double, LinkedList<Order>> askOrders = orderBook.getAskOrders();

        System.out.println("""
                BID ORDERS
                -------------------------------------------------------------
                """);
        bidOrders.forEach((k, v) -> System.out.printf("%-15s : %s%n", k, v));
        System.out.println("""
                \n ASK ORDERS
                -------------------------------------------------------------
                """);
        askOrders.forEach((k, v) -> System.out.printf("%-15s : %s%n", k, v));
        System.out.println();
    }
}
