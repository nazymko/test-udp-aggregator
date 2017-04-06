package org.nazymko.storage;

import org.junit.Test;
import org.nazymko.Config;
import org.nazymko.Runner;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class MultithreadingTest {

    @Test
    public void testIt() throws IOException, InterruptedException {

        final Runner runner = new Runner();
        runner.run();

        runner.getSocket().send(
                new DatagramPacket(
                        TestConstants.INCOME_MESSAGE.getBytes(),
                        TestConstants.INCOME_MESSAGE.getBytes().length,
                        InetAddress.getLocalHost(),
                        Config.PORT
                ));


        startSender(runner);

        Thread.sleep(300000);

    }

    private void startSender(final Runner runner) {
        new Thread(new EmulatorTask(runner.getSocket())).start();
        new Thread(new EmulatorTask(runner.getSocket())).start();
        new Thread(new EmulatorTask(runner.getSocket())).start();
        new Thread(new EmulatorTask(runner.getSocket(), false, true)).start();
        new Thread(new EmulatorTask(runner.getSocket(), true, true)).start();
        new Thread(new EmulatorTask(runner.getSocket(), true, false)).start();
    }
}
