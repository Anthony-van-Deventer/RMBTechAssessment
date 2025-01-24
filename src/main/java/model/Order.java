package model;

public class Order {

    private final int id;
    private final double price;
    private int quantity;
    private final Side side;


    public Order(int id, double price, int quantity, Side side) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.side = side;
    }

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Side getSide() {
        return side;
    }
}
