package org.nazymko;

import org.nazymko.workers.NetworkListener;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        new Thread(new NetworkListener()).start();

        System.out.println("Press 'Enter' to exit");
        System.in.read();

    }
}
