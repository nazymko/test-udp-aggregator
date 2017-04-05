package org.nazymko.messages.model.out;

import java.util.List;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class AggregatedResult {
    long outSequenceNumber;
    List<Product> products;

    public AggregatedResult(long outSequenceNumber, List<Product> products) {
        this.outSequenceNumber = outSequenceNumber;
        this.products = products;
    }
}
