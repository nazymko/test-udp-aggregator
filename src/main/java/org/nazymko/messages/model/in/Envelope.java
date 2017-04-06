package org.nazymko.messages.model.in;

import java.util.List;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class Envelope {
    public Long getInSequenceNumber() {
        return inSequenceNumber;
    }

    public List<Message> getMessages() {
        return messages;
    }

    private long inSequenceNumber;
    private List<Message> messages;

    public void setInSequenceNumber(long inSequenceNumber) {
        this.inSequenceNumber = inSequenceNumber;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Envelope(long inSequenceNumber) {
        this.inSequenceNumber = inSequenceNumber;
    }
}
