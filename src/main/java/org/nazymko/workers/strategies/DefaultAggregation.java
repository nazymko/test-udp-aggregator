package org.nazymko.workers.strategies;

import org.nazymko.messages.model.out.AggregatedProduct;
import org.nazymko.messages.model.out.AggregatedResult;
import org.nazymko.messages.model.out.Request;
import org.nazymko.storage.Aggregator;
import org.nazymko.storage.Order;
import org.nazymko.storage.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class DefaultAggregation implements Strategy {

    @Override
    public Object apply(Aggregator aggregator) {
        HashMap<String, Product> aggregated = aggregator.getAggregated();

        ArrayList<AggregatedProduct> products = new ArrayList<>();

        for (String productId : aggregated.keySet()) {
            Product product = aggregated.get(productId);

            final AggregatedProduct aggregatedProduct = aggregate(product);
            products.add(aggregatedProduct);
        }

        return new AggregatedResult(products);
    }

    private AggregatedProduct aggregate(Product product) {
        final HashMap<BigDecimal, Request> sellAggregation = aggregateByPrice(product.getSellLevels());
        final HashMap<BigDecimal, Request> buyAggregation = aggregateByPrice(product.getBuyLevels());


        AggregatedProduct aggregatedProduct = new AggregatedProduct(product.getProductId());

        aggregatedProduct.getBuyLevels().addAll(buyAggregation.values());
        aggregatedProduct.getSellLevels().addAll(sellAggregation.values());

        return aggregatedProduct;
    }

    private HashMap<BigDecimal, Request> aggregateByPrice(List<Order> buyLevels) {
        HashMap<BigDecimal, Request> aggregation = new HashMap<>();
        for (Order order : buyLevels) {
            if (!aggregation.containsKey(order.getPrice())) {

                aggregation.put(order.getPrice(), new Request(order.getPrice(), order.getQuantity()));
            } else {

                aggregation.get(order.getPrice()).getQuantity().addAndGet(order.getQuantity());
            }
        }

        return aggregation;
    }
}
