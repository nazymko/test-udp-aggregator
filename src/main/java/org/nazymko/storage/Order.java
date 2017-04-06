package org.nazymko.storage;

import org.nazymko.messages.model.in.Message;

import java.math.BigDecimal;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class Order {
    private final long orderId;
    private Message.Side side;
    private Product parent;
    private BigDecimal price;
    private long quantity;

    public Order(Product product, BigDecimal price, long quantity, long orderId, Message.Side side) {
        this.parent = product;
        this.price = price;
        this.quantity = quantity;
        this.orderId = orderId;
        this.side = side;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Product getParent() {
        return parent;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        return orderId == order.orderId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "side=" + side +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public int hashCode() {
        return (int) (orderId ^ (orderId >>> 32));
    }

    public long getOrderId() {
        return orderId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public long getQuantity() {
        return quantity;
    }

    public Message.Side getSide() {
        return side;
    }
}
