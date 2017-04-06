package org.nazymko.workers;

import org.nazymko.InjectionReplacement;
import org.nazymko.messages.model.in.Envelope;
import org.nazymko.storage.MetricsHolder;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class Processor implements Runnable {


    @Override
    public void run() {

        LinkedBlockingQueue<MetricsHolder.MeasurementObject> rawStorage = InjectionReplacement.AGGREGATOR.getRawStorage();

        while (true) {
            try {
                MetricsHolder.MeasurementObject message = rawStorage.take();

                message.setParsingStartAt(System.nanoTime());
                Envelope parsed = InjectionReplacement.GSON.fromJson(message.getRaw(), Envelope.class);
                message.setParsingFinishedAt(System.nanoTime());

                message.setEnvelope(parsed);
                if (isValid(message)) {
                    InjectionReplacement.AGGREGATOR.consume(message);
                }

            } catch (Throwable any) {
                InjectionReplacement.AGGREGATOR.onParsingError(any);
                System.out.println(Thread.currentThread().getName() + " - parsing finished with error[ " + any.getMessage() + "]");
                any.printStackTrace();
            }
        }
    }

    private boolean isValid(MetricsHolder.MeasurementObject message) {
        if (message.getEnvelope() == null || message.getEnvelope().getInSequenceNumber() == null || message.getEnvelope().getMessages() == null || message.getEnvelope().getMessages().isEmpty()) {
            return false;
        }
        return true;
    }
}
