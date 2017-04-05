package org.nazymko;

import com.google.gson.Gson;
import org.nazymko.storage.Aggregator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by nazymko.patronus@gmail.com
 */

public class InjectionReplacement {
    public static Gson GSON = new Gson();
    public static Aggregator AGGREGATOR = new Aggregator();
    public static ExecutorService EXECUTOR = Executors.newCachedThreadPool();

}
