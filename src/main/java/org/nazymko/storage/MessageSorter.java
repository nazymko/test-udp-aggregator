package org.nazymko.storage;

import org.nazymko.messages.model.in.Message;

import java.util.Comparator;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class MessageSorter implements Comparator<Message> {
    @Override
    public int compare(Message o1, Message o2) {
        if (o1.getSequenceId() == o2.getSequenceId()) {
            if (o1.getType() == o2.getType()) {
                return 0;
            }
            if (o2.getType() == Message.Type.addOrder) {
                return 1;
            }
            if (o2.getType() == Message.Type.deleteOrder) {
                return -1;
            }

            return 1;
        } else {
            return Long.compare(o1.getSequenceId(), o2.getSequenceId());
        }
    }
}
