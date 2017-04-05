package org.nazymko.messages.model.out;

import java.util.List;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class AggregatedResult {
    long outSequenceNumber = SequenceGenerator.next();
    List<AggregatedProduct> aggregatedProducts;

    public AggregatedResult(List<AggregatedProduct> aggregatedProducts) {
        this.aggregatedProducts = aggregatedProducts;
    }
}
