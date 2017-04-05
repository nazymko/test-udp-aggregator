package org.nazymko.workers.strategies;

import org.nazymko.storage.Aggregator;

/**
 * Created by nazymko.patronus@gmail.com
 */
public interface Strategy {
    Object apply(Aggregator aggregator);
}
