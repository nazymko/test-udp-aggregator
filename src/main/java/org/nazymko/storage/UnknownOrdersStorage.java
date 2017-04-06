package org.nazymko.storage;

import org.nazymko.InjectionReplacement;
import org.nazymko.messages.model.in.Message;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class UnknownOrdersStorage {
    private HashMap<Long, Message> deleteOrders = new HashMap<>();
    private HashMap<Long, List<Message>> changeOrders = new HashMap<>();

    public HashMap<Long, Message> getDeleteOrders() {
        return deleteOrders;
    }

    public HashMap<Long, List<Message>> getChangeOrders() {
        return changeOrders;
    }

    public void addChange(Message msg) {
        changeOrders.computeIfAbsent(msg.getOrderId(), (a) -> new LinkedList<Message>()).add(msg);
    }

    public void addDelete(Message msg) {
        deleteOrders.put(msg.getOrderId(), msg);
    }

    public boolean needIt(long orderId) {
        return deleteOrders.containsKey(orderId) || changeOrders.containsKey(orderId);
    }

    public void informIncome(Message msg) {
        final List<Message> message = new LinkedList<>();
        if (deleteOrders.containsKey(msg.getOrderId())) {
            message.add(deleteOrders.get(msg.getOrderId()));
        }

        if (changeOrders.containsKey(msg.getOrderId())) {
            message.addAll(changeOrders.get(msg.getOrderId()));
        }


        message.sort(InjectionReplacement.MESSAGE_SORTER);

        for (Message _m : message) {
            InjectionReplacement.AGGREGATOR.consumeMessage(_m);
        }

    }
}
