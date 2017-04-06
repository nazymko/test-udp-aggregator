package org.nazymko.workers;

import org.nazymko.InjectionReplacement;
import org.nazymko.messages.model.in.Envelope;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class Processor implements Runnable {


    @Override
    public void run() {

        LinkedBlockingQueue<String> rawStorage = InjectionReplacement.AGGREGATOR.getRawStorage();

        while (true) {
            try {
                String message = rawStorage.take();

                Envelope envelope = InjectionReplacement.GSON.fromJson(message, Envelope.class);
                InjectionReplacement.AGGREGATOR.consume(envelope);


            } catch (Throwable any) {
                InjectionReplacement.AGGREGATOR.onParsingError(any);
                System.out.println(Thread.currentThread().getName() + " - parsing finished with error[ " + any.getMessage() + "]");
            }
        }
    }
}
