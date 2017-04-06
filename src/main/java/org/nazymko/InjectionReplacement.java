package org.nazymko;

import com.google.gson.Gson;
import org.nazymko.storage.Aggregator;
import org.nazymko.storage.MessageSorter;
import org.nazymko.storage.UnknownOrdersStorage;
import org.nazymko.workers.strategies.RequestSorted;

import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by nazymko.patronus@gmail.com
 */

public class InjectionReplacement {
    public static Gson GSON = new Gson();
    public static Aggregator AGGREGATOR = new Aggregator();
    public static ExecutorService EXECUTOR = Executors.newCachedThreadPool();
    public static UnknownOrdersStorage MISSED_ORDERS_STORAGE = new UnknownOrdersStorage();
    public static MessageSorter MESSAGE_SORTER = new MessageSorter();
    public static RequestSorted REQUEST_ASC_SORTER = new RequestSorted(RequestSorted.SortOrder.ASC);
    public static RequestSorted REQUEST_DESC_SORTER = new RequestSorted(RequestSorted.SortOrder.DESC);

}
