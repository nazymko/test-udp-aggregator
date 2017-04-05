package org.nazymko.messages.model.out;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class Product {

    private String productId;

    public Product(String productId) {
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

    private List<Request> buyLevels;
    private List<Request> sellLevels;

}
