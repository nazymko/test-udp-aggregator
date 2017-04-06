package org.nazymko.storage;

import java.util.Iterator;
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
                remove(order, sellLevels);
                break;
            case buy:
                remove(order, buyLevels);
                break;
        }
    }

    private void remove(Order order, List<Order> from) {
        synchronized (from) {
            from.remove(order);
        }
    }
}
