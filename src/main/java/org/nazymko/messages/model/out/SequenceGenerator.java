package org.nazymko.messages.model.out;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class SequenceGenerator {
    private static final AtomicLong id = new AtomicLong(0);

    public static long next() {
        return id.incrementAndGet();
    }
}
