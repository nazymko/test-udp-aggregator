package org.nazymko.messages.model.out;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class SequenceGenerator {
    private final AtomicLong id = new AtomicLong(0);

    public long next() {
        return id.incrementAndGet();
    }
}
