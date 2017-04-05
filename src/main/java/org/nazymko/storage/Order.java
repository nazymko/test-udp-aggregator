package org.nazymko.storage;

import java.math.BigDecimal;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class Order {
    BigDecimal price;
    long quantity;
    long orderId;

    public Order(BigDecimal price, long quantity, long orderId) {
        this.price = price;
        this.quantity = quantity;
        this.orderId = orderId;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        return orderId == order.orderId;
    }

    @Override
    public int hashCode() {
        return (int) (orderId ^ (orderId >>> 32));
    }
}
