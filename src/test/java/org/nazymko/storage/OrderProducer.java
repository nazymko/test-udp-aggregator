package org.nazymko.storage;

import org.nazymko.messages.model.in.Envelope;
import org.nazymko.messages.model.in.Message;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class OrderProducer {
    private static final AtomicLong counter = new AtomicLong(0);
    private static final AtomicLong orders = new AtomicLong(0);

    private static List<String> products = Arrays.asList("APPLE", "IMB", "PWD", "FAW");

    public static Envelope next() {
        final Envelope envelope = new Envelope(counter.incrementAndGet());

        envelope.setMessages(messages());

        return envelope;
    }


    private static List<Message> messages() {
        final int v = (int) (Math.random() * 9) + 1;

        final ArrayList<Message> arrayList = new ArrayList<>();
        for (int i = 0; i < v; i++) {
            arrayList.add(message());
        }

        return arrayList;
    }

    private static Message message() {
        final int action = (int) (Math.random() * 3);
        final int product = (int) (Math.random() * 4);
        final int side = (int) (Math.random() * 2);
        final long quantity = (int) (Math.random() * 3) + 1;
        final Message.Type type = Message.Type.values()[action];
        BigDecimal price = new BigDecimal(Math.random() * 5).round(new MathContext(2));


        switch (type) {
            case addOrder:
                return new Message(Message.Type.addOrder, orders.incrementAndGet(), products.get(product), Message.Side.values()[side], price, quantity);
            case changeOrder:
                final long changeOrder = (long) (Math.random() * orders.get());
                return new Message(Message.Type.changeOrder, changeOrder, null, null, price, quantity);
            case deleteOrder:
                final long deleteOrder = (int) (Math.random() * orders.get());
                return new Message(Message.Type.deleteOrder, deleteOrder, null, null, null, null);
            default:
                return null;
        }
    }

}
