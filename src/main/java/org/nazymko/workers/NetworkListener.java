package org.nazymko.workers;

import org.nazymko.Config;
import org.nazymko.InjectionReplacement;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by a.nazimko on 05.04.2017.
 */
public class NetworkListener implements Runnable {

    public static final int DATA_MAX_SIZE = 1024 * 65;

    public NetworkListener(DatagramSocket in) {
        this.in = in;
    }

    public NetworkListener() throws SocketException {
        this.in = new DatagramSocket(Config.PORT);
    }

    private final DatagramSocket in;

    @Override
    public void run() {
        System.out.println("Server Started");

        while (true) {
            try {
                byte[] receiveData = new byte[DATA_MAX_SIZE];
                DatagramPacket received = new DatagramPacket(receiveData, DATA_MAX_SIZE);
                in.receive(received);

                String msg = new String(received.getData(), 0, received.getLength());

                InjectionReplacement.AGGREGATOR.addRaw(msg);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
