package org.nazymko.messages.model.out;

import org.nazymko.InjectionReplacement;

import java.util.*;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class AggregatedProduct {

    private String productId;
    private SortedSet<Request> buyLevels;
    private SortedSet<Request> sellLevels;

    public AggregatedProduct(String productId) {
        this.productId = productId;
        this.buyLevels = new TreeSet<>(InjectionReplacement.REQUEST_DESC_SORTER);
        this.sellLevels = new TreeSet<>(InjectionReplacement.REQUEST_ASC_SORTER);
    }

    public String getProductId() {
        return productId;
    }

    public Collection<Request> getBuyLevels() {
        return buyLevels;
    }

    public Collection<Request> getSellLevels() {
        return sellLevels;
    }

}
