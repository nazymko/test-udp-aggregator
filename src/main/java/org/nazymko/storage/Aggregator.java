package org.nazymko.storage;


import org.nazymko.messages.model.in.Envelope;
import org.nazymko.messages.model.in.Message;

import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class Aggregator {

    protected HashMap<String, Product> aggregated = new HashMap<String, Product>();
    protected HashMap<Long, Order> orders = new HashMap<Long, Order>();
    protected LinkedBlockingQueue<String> rawStorage = new LinkedBlockingQueue<String>();

    public HashMap<String, Product> getAggregated() {
        return aggregated;
    }

    public void addRaw(String udpMessage) {
        rawStorage.add(udpMessage);
    }

    public LinkedBlockingQueue<String> getRawStorage() {
        return rawStorage;
    }

    public void consume(Envelope message) {

        for (Message msg : message.getMessages()) {
            switch (msg.getType()) {
                case addOrder:
                    addOrderAction(msg);
                    break;
                case changeOrder:
                    changeOrderAction(msg);
                    break;
                case deleteOrder:
                    deleteOrderAction(msg);
                    break;
            }
        }
    }

    private void deleteOrderAction(Message msg) {
        if (orders.containsKey(msg.getOrderId())) {
            Order order = orders.get(msg.getOrderId());

            order.getParent().detach(order);
            orders.remove(msg.getOrderId());

        } else {
            System.err.println("Order not found. Id : " + msg.getOrderId());
        }
    }

    private void addOrderAction(Message msg) {

        if (!aggregated.containsKey(msg.getProductId())) {
            aggregated.put(msg.getProductId(), new Product(msg.getProductId()));
        }

        final Product product = aggregated.get(msg.getProductId());

        final Order order = new Order(
                product,
                msg.getPrice(),
                msg.getQuantity(),
                msg.getOrderId(),
                msg.getSide()
        );

        switch (msg.getSide()) {
            case sell:
                product.getSellLevels().add(order);
                break;
            case buy:
                product.getBuyLevels().add(order);
                break;
        }
        orders.put(msg.getOrderId(), order);

    }

    private void changeOrderAction(Message msg) {
        if (orders.containsKey(msg.getOrderId())) {
            final Order order = orders.get(msg.getOrderId());

            order.setPrice(msg.getPrice());
            order.setQuantity(msg.getQuantity());

        } else {
            System.err.println("Order not found. Id : " + msg.getOrderId());
        }

    }

}
