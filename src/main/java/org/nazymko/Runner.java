package org.nazymko;

import org.nazymko.workers.DataProducer;
import org.nazymko.workers.NetworkListener;
import org.nazymko.workers.Processor;

import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class Runner {
    private DatagramSocket socket;

    public void run() throws SocketException {

        socket = new DatagramSocket(Config.PORT);
        InjectionReplacement.EXECUTOR.submit(new NetworkListener(socket));

        InjectionReplacement.EXECUTOR.submit(new Processor());
        InjectionReplacement.EXECUTOR.submit(new Processor());

        InjectionReplacement.EXECUTOR.submit(new DataProducer(new DatagramSocket(Config.OUT_PORT)));

    }

    public DatagramSocket getSocket() {
        return socket;
    }
}
