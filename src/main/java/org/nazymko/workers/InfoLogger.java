package org.nazymko.workers;

import org.nazymko.InjectionReplacement;
import org.nazymko.storage.MetricsHolder;

import java.util.LinkedList;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class InfoLogger implements Runnable {
    @Override
    public void run() {
        while (true) {
            try {
                try {
                    Thread.sleep(5_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("----BEGIN-------Information gathered for last 5 seconds-------BEGIN-----");
                printInfo();

                printStats();
                System.out.println("-----END--------Information gathered for last 5 seconds--------END------");

            } catch (Throwable any) {
                any.printStackTrace();
            }
        }


    }

    private void printStats() {
        LinkedList<MetricsHolder.NetworkMetric> processingMetric = InjectionReplacement.METRIC_HOLDER.getProcessingMetric();
        LinkedList<MetricsHolder.NetworkMetric> copy = (LinkedList) processingMetric.clone();

        for (MetricsHolder.NetworkMetric networkMetric : copy) {
            processingMetric.remove(networkMetric);
        }

        long sum = copy.stream().mapToLong(MetricsHolder.NetworkMetric::getProcessed).sum();
        long min = copy.stream().mapToLong(MetricsHolder.NetworkMetric::getProcessed).min().getAsLong();
        long max = copy.stream().mapToLong(MetricsHolder.NetworkMetric::getProcessed).max().getAsLong();


        long averageProcessingTime = sum / copy.size();

        String PROCESSING_METRICS = new StringBuilder()
                .append("---------------------METRICS-----------------------\n")
                .append("Elements processed      :").append(copy.size()).append("\n")
                .append("Average processing time :").append(averageProcessingTime).append("ns.\n")
                .append("Minimal processing time :").append(min).append(" ns.\n")
                .append("Maximal processing time :").append(max).append("ns.\n")
                .append("---------------------METRICS-----------------------").append("\n").toString();

        System.out.println(PROCESSING_METRICS);
    }

    private void printInfo() {
        String AGGREGATOR_INFO = InjectionReplacement.AGGREGATOR.info();
        System.out.println(AGGREGATOR_INFO);
    }
}
