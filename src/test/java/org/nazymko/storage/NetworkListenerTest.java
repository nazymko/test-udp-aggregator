package org.nazymko.storage;

import org.junit.Test;
import org.nazymko.Config;
import org.nazymko.Runner;

import java.io.IOException;
import java.net.*;

import static org.junit.Assert.*;

/**
 * Created by a.nazimko on 05.04.2017.
 */
public class NetworkListenerTest {

    @Test
    public void testIt() throws IOException, InterruptedException {

        Runner runner = new Runner();
        runner.run();

        runner.getSocket().send(
                new DatagramPacket(
                        TestConstants.INCOME_MESSAGE.getBytes(),
                        TestConstants.INCOME_MESSAGE.getBytes().length,
                        InetAddress.getLocalHost(),
                        Config.PORT
                ));

        Thread.sleep(1000);

    }

}