package org.nazymko.storage;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class MetricsHolder {
    private Queue<NetworkMetric> networkMetric = new LinkedBlockingQueue<>(64);
    private Queue<NetworkMetric> processingMetric = new LinkedList<>();

    public Queue<NetworkMetric> getNetworkMetric() {
        return networkMetric;
    }

    public Queue<NetworkMetric> getProcessingMetric() {
        return processingMetric;
    }

    class NetworkMetric {
        long processed;
        long inQueue;

        public NetworkMetric(long processed, long inQueue) {
            this.processed = processed;
            this.inQueue = inQueue;
        }
    }
}
