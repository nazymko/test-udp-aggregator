package org.nazymko.storage;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class Product {
    private String productId;
    private List<Order> buyLevels;
    private List<Order> sellLevels;

    public Product(String productId) {
        this.productId = productId;
        this.buyLevels = new LinkedList<Order>();
        this.sellLevels = new LinkedList<Order>();
    }


    public String getProductId() {
        return productId;
    }

    public List<Order> getBuyLevels() {
        return buyLevels;
    }

    public List<Order> getSellLevels() {
        return sellLevels;
    }

    public void detach(Order order) {
        switch (order.getSide()) {
            case sell:
                sellLevels.remove(order);
                break;
            case buy:
                buyLevels.remove(order);
                break;
        }
    }
}
