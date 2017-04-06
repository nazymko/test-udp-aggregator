package org.nazymko.storage;


import org.nazymko.InjectionReplacement;
import org.nazymko.messages.model.in.Envelope;
import org.nazymko.messages.model.in.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class Aggregator {

    private final Set<Long> processed = new ConcurrentSkipListSet<>();
    HashMap<Long, Order> orders = new HashMap<Long, Order>();
    private AtomicLong duplicateCounter = new AtomicLong(0);
    private AtomicLong envelopeCounter = new AtomicLong(0);
    private AtomicLong parsedWithError = new AtomicLong(0);
    private LinkedBlockingQueue<MetricsHolder.MeasurementObject> rawStorage = new LinkedBlockingQueue<>();
    private HashMap<String, Product> aggregated = new HashMap<String, Product>();

    public String info() {
        return new StringBuilder().append(" ------------INFO-------------- ").append("\n")
                .append("Products on the track  \t: ").append(aggregated.size()).append("\n")
                .append("Orders in storage      \t: ").append(orders.size()).append("\n")
                .append("UDP Messages processed \t: ").append(envelopeCounter.get()).append("\n")
                .append("UDP Messages to process\t: ").append(rawStorage.size()).append("\n")
                .append("UDP Messages with error\t: ").append(parsedWithError.get()).append("\n")
                .append("UDP duplicates found   \t: ").append(duplicateCounter.get()).append("\n")
                .append(" ------------INFO-------------- ").toString();
    }

    public HashMap<String, Product> getAggregated() {
        return aggregated;
    }

    public void addRaw(String udpMessage) {
        rawStorage.add(new MetricsHolder.MeasurementObject(udpMessage));
    }

    public LinkedBlockingQueue<MetricsHolder.MeasurementObject> getRawStorage() {
        return rawStorage;
    }

    public void consume(MetricsHolder.MeasurementObject message) {
        Envelope envelope = message.getEnvelope();
        if (processed.contains(envelope.getInSequenceNumber())) {
            duplicateCounter.incrementAndGet();
//            System.err.println("Message duplicate. Sequence id : " + envelope.getInSequenceNumber());
            return;
        }

        for (Message msg : envelope.getMessages()) {
            msg.setSequenceId(envelope.getInSequenceNumber());
            consumeMessage(msg);

        }
        message.setFinishedNS(System.nanoTime());
        onConsumingDone(message);
    }

    private void onConsumingDone(MetricsHolder.MeasurementObject message) {
        processed.add(message.getEnvelope().getInSequenceNumber());
        envelopeCounter.incrementAndGet();
        InjectionReplacement.METRIC_HOLDER.getProcessingMetric().add(new MetricsHolder.NetworkMetric(message));
    }

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
            if (order == null) {
                return;
            }
            synchronized (order.getParent()) {
                if (order.getParent() != null) {
                    order.getParent().detach(order);
                }
            }
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
        List<Order> orders = null;
        switch (msg.getSide()) {
            case sell:
                orders = product.getSellLevels();
                break;
            case buy:
                orders = product.getBuyLevels();
                break;
            default:
                return;
        }

        synchronized (orders) {
            orders.add(order);
        }

        this.orders.put(msg.getOrderId(), order);

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

    void consumeMessage(Message m) {
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
}
