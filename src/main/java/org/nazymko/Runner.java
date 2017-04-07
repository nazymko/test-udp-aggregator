package org.nazymko;

import org.nazymko.workers.DataProducer;
import org.nazymko.workers.InfoLogger;
import org.nazymko.workers.NetworkListener;
import org.nazymko.workers.Processor;

import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class Runner {
    private DatagramSocket socket;
    private int processorThreadCount;

    public Runner(int processorThreadCount) {

        this.processorThreadCount = processorThreadCount;
    }

    public void start() throws SocketException {

        socket = new DatagramSocket(Config.PORT);
        InjectionReplacement.EXECUTOR.submit(new NetworkListener(socket));
        for (int i = 0; i < processorThreadCount; i++) {
            InjectionReplacement.EXECUTOR.submit(new Processor());

        }
        InjectionReplacement.EXECUTOR.submit(new InfoLogger());
        InjectionReplacement.EXECUTOR.submit(new DataProducer(new DatagramSocket(Config.OUT_PORT)));

    }

    public DatagramSocket getSocket() {
        return socket;
    }
}
