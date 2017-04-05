package org.nazymko.messages.model.out;

import java.math.BigDecimal;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class Request {
    private BigDecimal price;
    private long quantity;

    public Request(BigDecimal price, long quantity) {
        this.price = price;
        this.quantity = quantity;
    }

}
