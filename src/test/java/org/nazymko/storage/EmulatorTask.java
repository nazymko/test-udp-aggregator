package org.nazymko.storage;

import org.nazymko.Config;
import org.nazymko.InjectionReplacement;
import org.nazymko.messages.model.in.Envelope;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class EmulatorTask implements Runnable {
    private final double sendDuplicate = 0.1;
    private final double lostPackageChance = 0.05;
    private DatagramSocket out;
    private boolean features;
    private boolean duplicatesAllowed = false;
    private boolean emulateLostPackage;

    public EmulatorTask(DatagramSocket out) {
        this.out = out;
    }


    public EmulatorTask(DatagramSocket out, boolean duplicatesAllowed, boolean emulateLostPackage) {
        this.out = out;
        this.duplicatesAllowed = duplicatesAllowed;
        this.emulateLostPackage = emulateLostPackage;
    }

    @Override
    public void run() {
        try {
            Envelope envelope = null;
            while (true) {
                envelope = newOrDuplicated(envelope);

                if (lostPackage()) {
                    continue;
                }

                final String message = InjectionReplacement.GSON.toJson(envelope);

                final byte[] dataToSend = message.getBytes();
                out.send(
                        new DatagramPacket(
                                dataToSend,
                                dataToSend.length,
                                InetAddress.getLocalHost(),
                                Config.PORT
                        )
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean lostPackage() {
        return emulateLostPackage && Math.random() < lostPackageChance;
    }

    private Envelope newOrDuplicated(Envelope previousEnvelope) {
        if (duplicatesAllowed && previousEnvelope != null) {
            final boolean emulateIt = Math.random() > sendDuplicate;

            if (!emulateIt) {
                return OrderProducer.next();
            } else {
                return previousEnvelope;
            }
        } else {
            return OrderProducer.next();

        }
    }
}
