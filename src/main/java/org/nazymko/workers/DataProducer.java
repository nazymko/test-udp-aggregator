package org.nazymko.workers;

import org.nazymko.Config;
import org.nazymko.InjectionReplacement;
import org.nazymko.workers.strategies.DefaultAggregation;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;

import static java.lang.Thread.sleep;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class DataProducer implements Runnable {
    public static final long INTERVAL = 2000L;
    private DatagramSocket out;
    private Long checkPoint = 0L;

    public DataProducer(DatagramSocket in) {
        this.out = in;
    }

    @Override
    public void run() {
        try {
            sleep(INTERVAL);

            while (true) {
                safeLoop();
            }


        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    private void safeLoop() throws IOException, InterruptedException {
        try {
            if (timeHasCome(checkPoint)) {
                checkPoint = System.currentTimeMillis();

                long measurement = System.nanoTime();


                final Object apply = new DefaultAggregation().apply(InjectionReplacement.AGGREGATOR);
                final String jsonData = InjectionReplacement.GSON.toJson(apply);
                final long aggregation = System.nanoTime() - measurement;
                System.out.println(timeNow() + "Aggregation : \t" + aggregation + " ns.");

                final byte[] dataToSend = jsonData.getBytes();

                safeSend(dataToSend);

                final long aggregationTime = (System.currentTimeMillis() - checkPoint);

                System.out.println(timeNow() + "Aggregation total time : " + aggregationTime + " ms");

                if (aggregationTime < INTERVAL) {
                    long sleepTime = INTERVAL - aggregationTime;
                    System.out.println(timeNow() + "Sleep for :" + sleepTime + " ms.");
                    Thread.sleep(sleepTime);
                } else {
                    System.out.println(timeNow() + "Starting next step immediately");
                }
            } else {
                final long timeGoneFromLastExecution = System.currentTimeMillis() - checkPoint;
                System.out.println(timeNow() + "Accidentally waked up early");

                final long timeToWait = INTERVAL - timeGoneFromLastExecution;

                System.out.println(timeNow() + "Sleep again for " + timeToWait + " ms");

                if (timeToWait > 0) {
                    Thread.sleep(timeToWait);
                }
            }
        } catch (Throwable any) {
            System.out.println("Unexpected exception : " + firstNotNull(any.getLocalizedMessage(), any.getMessage()));
            any.printStackTrace();
        }
    }

    private Object firstNotNull(Object... any) {
        for (Object o : any) {
            if (o != null) {
                return o;
            }

        }
        return null;
    }

    private void safeSend(byte[] dataToSend) throws IOException {
        try {
            if (dataToSend.length < (1024 * 64) - 28) {
                out.send(
                        new DatagramPacket(
                                dataToSend,
                                dataToSend.length,
                                InetAddress.getByName(Config.OUT_ADDRESS),
                                Config.OUT_PORT
                        ));
            } else {
                System.err.println("\nMessage too big to be send over UDP.\n");

            }
        } catch (SocketException exception) {
            exception.printStackTrace();
        }
    }

    private String timeNow() {
        return new Date(System.currentTimeMillis()) + " :";
    }

    private boolean timeHasCome(long lastCheckpoint) {
        final long timeFromLastRan = System.currentTimeMillis() - lastCheckpoint;
        return timeFromLastRan >= INTERVAL;
    }
}
