package org.nazymko.messages.model.in;

import java.math.BigDecimal;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class Message {
    private Type type;
    private long orderId;
    private String productId;
    private Side side;
    private BigDecimal price;
    private long quantity;

    public Type getType() {
        return type;
    }

    public long getOrderId() {
        return orderId;
    }

    public String getProductId() {
        return productId;
    }

    public Side getSide() {
        return side;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public long getQuantity() {
        return quantity;
    }

    public enum Side {
        sell, buy
    }

    public enum Type {
        addOrder, changeOrder, deleteOrder

    }
}
