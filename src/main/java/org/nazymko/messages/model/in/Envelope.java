package org.nazymko.messages.model.in;

import java.util.List;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class Envelope {
    public long getInSequenceNumber() {
        return inSequenceNumber;
    }

    public List<Message> getMessages() {
        return messages;
    }

    private long inSequenceNumber;
    private List<Message> messages;


}
