package org.nazymko.storage;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class Product {
    private String productId;
    private HashMap<Long, Order> orders;
    private List<Order> buyLevels;
    private List<Order> sellLevels;

    public Product(String productId) {
        this.productId = productId;
        this.orders = new HashMap<Long, Order>();
        this.buyLevels = new LinkedList<Order>();
        this.sellLevels = new LinkedList<Order>();
    }

    public HashMap<Long, Order> getOrders() {
        return orders;
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

    public void registerOrder(long orderId, Order order) {
        orders.put(orderId, order);
    }
}
