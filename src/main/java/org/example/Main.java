package org.example;

import model.Side;
import service.MatchingEngine;
import service.OrderBook;
import service.OrderBookPrinter;

public class Main {
    public static void main(String[] args) {
        System.out.println("Construct the example order book\n");
        OrderBook orderBook = new OrderBook();
        MatchingEngine matchingEngine = new MatchingEngine(orderBook);

        //setup BID orders
        orderBook.addOrder(9,40, Side.BUY);
        orderBook.addOrder(9,20, Side.BUY);
        orderBook.addOrder(8,30, Side.BUY);
        orderBook.addOrder(8,20, Side.BUY);
        orderBook.addOrder(7,50, Side.BUY);
        orderBook.addOrder(7,50, Side.BUY);

        //setup ASK orders
        orderBook.addOrder(10,5, Side.SELL);
        orderBook.addOrder(10,100, Side.SELL);
        orderBook.addOrder(11,40, Side.SELL);
        orderBook.addOrder(11,50, Side.SELL);
        orderBook.addOrder(12,20, Side.SELL);
        orderBook.addOrder(12,10, Side.SELL);


        System.out.println("Example execution with new SELL order at price of 9 and amount of 55 arrives to\n" +
                "exchange\n");
        System.out.println("OrderBook before filling order: \n");
        OrderBookPrinter.printOrderBook(orderBook);
        matchingEngine.fillOrder(9, 55, Side.SELL);
        System.out.println("OrderBook after filling order: \n");
        OrderBookPrinter.printOrderBook(orderBook);
    }
}