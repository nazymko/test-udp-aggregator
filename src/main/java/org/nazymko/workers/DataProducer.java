package org.nazymko.workers;

import org.nazymko.InjectionReplacement;
import org.nazymko.workers.strategies.DefaultAggregation;

import java.net.DatagramSocket;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class DataProducer implements Runnable {
    private DatagramSocket in;

    public DataProducer(DatagramSocket in) {
        this.in = in;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000L);
            long now = System.currentTimeMillis();

            while (true) {
                new DefaultAggregation().apply(InjectionReplacement.AGGREGATOR);


            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
