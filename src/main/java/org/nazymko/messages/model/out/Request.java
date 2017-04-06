package org.nazymko.messages.model.out;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class Request {
    private BigDecimal price;
    private AtomicLong quantity;

    public Request(BigDecimal price, long quantity) {
        this.price = price;
        this.quantity = new AtomicLong(quantity);
    }

    public BigDecimal getPrice() {
        return price;
    }

    public AtomicLong getQuantity() {
        return quantity;
    }
}
