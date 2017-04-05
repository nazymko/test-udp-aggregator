package org.nazymko.workers.strategies;

import org.nazymko.messages.model.out.AggregatedProduct;
import org.nazymko.messages.model.out.AggregatedResult;
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

        ArrayList<Object> list = new ArrayList<>();

        for (String productId : aggregated.keySet()) {
            Product product = aggregated.get(productId);


            aggregate(product)
        }

        new AggregatedResult()
    }

    private AggregatedProduct aggregate(Product product) {
        HashMap<BigDecimal, Long> priceCountMap = new HashMap<>();

        List<Order> buyLevels = product.getBuyLevels();
        //TODO: refactor fol multithreading
        for (Order order : buyLevels) {
            if (!priceCountMap.containsKey(order.getPrice())) {

                priceCountMap.put(order.getPrice(), order.getQuantity());
            } else {

                Long quantity = priceCountMap.get(order.getPrice());
                priceCountMap.put(order.getPrice(), order.getQuantity() + quantity);
            }

        }
        AggregatedProduct aggregatedProduct = new AggregatedProduct(product.getProductId());


        return aggregatedProduct;
    }
}
