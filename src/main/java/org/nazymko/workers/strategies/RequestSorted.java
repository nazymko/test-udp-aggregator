package org.nazymko.workers.strategies;

import org.nazymko.messages.model.out.Request;


/**
 * Created by nazymko.patronus@gmail.com
 */
public class RequestSorted implements java.util.Comparator<org.nazymko.messages.model.out.Request> {

    private SortOrder order;

    public RequestSorted(SortOrder order) {
        this.order = order;
    }

    @Override
    public int compare(Request o1, Request o2) {
        switch (order) {
            case ASC:
                return o1.getPrice().compareTo(o2.getPrice());
            case DESC:
                return o2.getPrice().compareTo(o1.getPrice());
        }
        return 0;
    }

    public static enum SortOrder {
        ASC, DESC
    }
}
