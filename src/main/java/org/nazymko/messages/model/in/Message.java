package org.nazymko.messages.model.in;

import java.math.BigDecimal;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class Message {
    private Type type;
    private Long orderId;
    private String productId;
    private Side side;
    private BigDecimal price;
    private Long quantity;
    private transient long sequenceId;

    public long getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(long sequenceId) {
        this.sequenceId = sequenceId;
    }

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

    public Message() {
    }

    public Message(Type type, Long orderId, String productId, Side side, BigDecimal price, Long quantity) {
        this.type = type;
        this.orderId = orderId;
        this.productId = productId;
        this.side = side;
        this.price = price;
        this.quantity = quantity;
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
