package org.nazymko.storage;

import org.junit.Test;
import org.nazymko.Runner;

import java.io.IOException;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class MultithreadingTest {

    @Test
    public void testIt() throws IOException, InterruptedException {

        Runner runner = new Runner(3);
        runner.start();



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
