package org.nazymko.storage;


import org.nazymko.InjectionReplacement;
import org.nazymko.messages.model.in.Envelope;
import org.nazymko.messages.model.in.Message;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class Aggregator {

    public void info() {
        System.out.println(" ------------INFO-------------- ");
        System.out.println("Products in storage    \t: " + aggregated.size());
        System.out.println("Orders in storage      \t: " + orders.size());
        System.out.println("UDP Messages processed \t: " + envelopeCounter.get());
        System.out.println("UDP Messages to process\t: " + rawStorage.size());
        System.out.println("UDP Messages with error\t: " + parsedWithError.get());
        System.out.println(" ------------INFO-------------- ");
    }

    private LinkedBlockingQueue<String> rawStorage = new LinkedBlockingQueue<String>();

    private HashMap<String, Product> aggregated = new HashMap<String, Product>();
    HashMap<Long, Order> orders = new HashMap<Long, Order>();
    protected AtomicLong duplicateCounter = new AtomicLong(0);
    private final Set<Long> processed = new TreeSet<>();

    public HashMap<String, Product> getAggregated() {
        return aggregated;
    }

    public void addRaw(String udpMessage) {
        rawStorage.add(udpMessage);
    }

    public LinkedBlockingQueue<String> getRawStorage() {
        return rawStorage;
    }

    public void consume(Envelope envelope) {

        if (processed.contains(envelope.getInSequenceNumber())) {
//            System.err.println("Message duplicate. Sequence id : " + envelope.getInSequenceNumber());
            return;
        }


        for (Message msg : envelope.getMessages()) {
            msg.setSequenceId(envelope.getInSequenceNumber());


            switch (msg.getType()) {
                case addOrder:
                    onAddOrder(msg);
                    break;
                case changeOrder:
                    onChangeOrder(msg);
                    break;
                case deleteOrder:
                    onDeleteOrder(msg);
                    break;
            }
        }

        onConsumingDone(envelope);
    }

    private void onConsumingDone(Envelope envelope) {
        processed.add(envelope.getInSequenceNumber());
        envelopeCounter.incrementAndGet();
    }

    AtomicLong envelopeCounter = new AtomicLong(0);

    private void onAddOrder(Message msg) {
        if (!orders.containsKey(msg.getOrderId())) {
            addOrderAction(msg);
            if (InjectionReplacement.MISSED_ORDERS_STORAGE.needIt(msg.getOrderId())) {
                InjectionReplacement.MISSED_ORDERS_STORAGE.informIncome(msg);
            }
        } else {

//            System.err.println("Found repeated order id : " + msg.getOrderId());
        }
    }

    private void onDeleteOrder(Message msg) {
        if (orders.containsKey(msg.getOrderId())) {
            deleteOrderAction(msg);
        } else {
            InjectionReplacement.MISSED_ORDERS_STORAGE.addDelete(msg);

        }
    }

    private void onChangeOrder(Message msg) {
        if (orders.containsKey(msg.getOrderId())) {
            changeOrderAction(msg);
        } else {
            InjectionReplacement.MISSED_ORDERS_STORAGE.addChange(msg);
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

    public void consumeMessage(Message m) {
        switch (m.getType()) {
            case addOrder:
                onAddOrder(m);
                break;
            case changeOrder:
                onChangeOrder(m);
                break;
            case deleteOrder:
                onDeleteOrder(m);
                break;
        }
    }

    public void onParsingError(Throwable any) {
        parsedWithError.getAndIncrement();
    }

    AtomicLong parsedWithError = new AtomicLong(0);
}
