package org.nazymko.messages.model.out;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class AggregatedProduct {

    private String productId;
    private List<Request> buyLevels;
    private List<Request> sellLevels;

    public AggregatedProduct(String productId) {
        this.productId = productId;
        this.buyLevels = new LinkedList<Request>();
        this.sellLevels = new LinkedList<Request>();
    }

    public String getProductId() {
        return productId;
    }

    public List<Request> getBuyLevels() {
        return buyLevels;
    }

    public List<Request> getSellLevels() {
        return sellLevels;
    }

}
