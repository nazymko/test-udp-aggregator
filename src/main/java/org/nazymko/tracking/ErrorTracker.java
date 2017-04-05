package org.nazymko.tracking;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class ErrorTracker {
    private LinkedBlockingQueue<String> errorMessagesQueue = new LinkedBlockingQueue<String>();
    private LinkedBlockingQueue<String> exceptionsQueue = new LinkedBlockingQueue<String>();


}
