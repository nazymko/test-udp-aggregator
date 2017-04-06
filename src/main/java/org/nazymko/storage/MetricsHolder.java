package org.nazymko.storage;

import org.nazymko.messages.model.in.Envelope;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class MetricsHolder {
    private LinkedBlockingQueue<NetworkMetric> networkMetric = new LinkedBlockingQueue<>(64);
    private LinkedList<NetworkMetric> processingMetric = new LinkedList<>();

    public Queue<NetworkMetric> getNetworkMetric() {
        return networkMetric;
    }

    public LinkedList<NetworkMetric> getProcessingMetric() {
        return processingMetric;
    }

    public static class MeasurementObject {
        long startedNS;
        long parsingStartAt;
        long parsingFinishedAt;
        long finishedNS;
        private Envelope envelope;
        private String raw;

        public MeasurementObject(String raw) {
            this.raw = raw;
            this.startedNS = System.nanoTime();
        }

        public MeasurementObject(Envelope envelope) {

            this.envelope = envelope;
        }

        public Envelope getEnvelope() {
            return envelope;
        }

        public void setEnvelope(Envelope envelope) {
            this.envelope = envelope;
        }

        public String getRaw() {

            return raw;
        }

        public void setParsingStartAt(long parsingStartAt) {
            this.parsingStartAt = parsingStartAt;
        }

        public void setParsingFinishedAt(long parsingFinishedAt) {
            this.parsingFinishedAt = parsingFinishedAt;
        }

        public void setStartedNS(long startedNS) {
            this.startedNS = startedNS;
        }

        public void setFinishedNS(long finishedNS) {
            this.finishedNS = finishedNS;
        }
    }

    public static class NetworkMetric {
        long processed;
        long inQueue;

        public NetworkMetric(long processed, long inQueue) {
            this.processed = processed;
            this.inQueue = inQueue;
        }

        public NetworkMetric(MeasurementObject message) {
            processed = message.finishedNS - message.startedNS;
            inQueue = message.parsingStartAt - message.startedNS;
        }

        public long getProcessed() {
            return processed;
        }

        public long getInQueue() {
            return inQueue;
        }
    }
}
