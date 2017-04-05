package org.nazymko.storage;


import org.nazymko.messages.model.in.Envelope;
import org.nazymko.messages.model.in.Message;

import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class Aggregator {

    private LinkedBlockingQueue<String> rawStorage = new LinkedBlockingQueue<String>();

    HashMap<String, Product> aggregated = new HashMap<String, Product>();
    HashMap<Long, Order> orders = new HashMap<Long, Order>();

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

    }

    private void addOrderAction(Message msg) {

        if (!aggregated.containsKey(msg.getProductId())) {
            aggregated.put(msg.getProductId(), new Product(msg.getProductId()));
        }

        final Product product = aggregated.get(msg.getProductId());

        final Order order = new Order(msg.getPrice(), msg.getQuantity(), msg.getOrderId());
        switch (msg.getSide()) {
            case sell:
                product.getSellLevels().add(order);
                break;
            case buy:
                product.getBuyLevels().add(order);
                break;
        }
        orders.put(msg.getOrderId(), order);
        product.registerOrder(msg.getOrderId(), order);

    }

    private void changeOrderAction(Message msg) {
        if (orders.containsKey(msg.getOrderId())) {
            final Order order = orders.get(msg.getOrderId());
        } else {
            System.err.println("Order not found. Id : " + msg.getOrderId());
        }

    }

}
