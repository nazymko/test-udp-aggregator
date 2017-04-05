package org.nazymko.storage;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.nazymko.InjectionReplacement;
import org.nazymko.messages.model.in.Envelope;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class AggregatorTest {
    @Test
    public void addAndDeleteTest() throws Exception {
        final Envelope envelopeAddSell = InjectionReplacement.GSON.fromJson(TestConstants.INCOME_MESSAGE_SINGLE_SELL, Envelope.class);

        Assertions.assertThat(envelopeAddSell.getInSequenceNumber()).isEqualTo(2);
        Assertions.assertThat(envelopeAddSell.getMessages()).hasSize(1);

        InjectionReplacement.AGGREGATOR.consume(envelopeAddSell);


        Assertions.assertThat(InjectionReplacement.AGGREGATOR.getAggregated()).containsKeys("APPLE");
        Assertions.assertThat(InjectionReplacement.AGGREGATOR.getAggregated().get("APPLE").getBuyLevels()).hasSize(0);
        Assertions.assertThat(InjectionReplacement.AGGREGATOR.getAggregated().get("APPLE").getSellLevels()).hasSize(1);
        Assertions.assertThat(InjectionReplacement.AGGREGATOR.getAggregated().get("APPLE").getSellLevels().get(0).getOrderId()).isEqualTo(3);
        Assertions.assertThat(InjectionReplacement.AGGREGATOR.orders).isNotEmpty();
        Assertions.assertThat(InjectionReplacement.AGGREGATOR.orders).containsKeys(3L);

        final Envelope envelopeDeleteSell = InjectionReplacement.GSON.fromJson(TestConstants.INCOME_MESSAGE_DELETE_SINGLE_SELL, Envelope.class);

        InjectionReplacement.AGGREGATOR.consume(envelopeDeleteSell);

        Assertions.assertThat(InjectionReplacement.AGGREGATOR.getAggregated()).containsKeys("APPLE");
        Assertions.assertThat(InjectionReplacement.AGGREGATOR.getAggregated().get("APPLE").getBuyLevels()).hasSize(0);
        Assertions.assertThat(InjectionReplacement.AGGREGATOR.getAggregated().get("APPLE").getSellLevels()).hasSize(0);
        Assertions.assertThat(InjectionReplacement.AGGREGATOR.getAggregated().get("APPLE").getSellLevels()).hasSize(0);
        Assertions.assertThat(InjectionReplacement.AGGREGATOR.orders).doesNotContainKeys(3L);
        Assertions.assertThat(InjectionReplacement.AGGREGATOR.orders).isEmpty();

    }


    @Test
    public void changeTest() throws Exception {
        final Envelope envelopeAddSell = InjectionReplacement.GSON.fromJson(TestConstants.INCOME_MESSAGE_SINGLE_SELL, Envelope.class);

        Assertions.assertThat(envelopeAddSell.getInSequenceNumber()).isEqualTo(2);
        Assertions.assertThat(envelopeAddSell.getMessages()).hasSize(1);

        InjectionReplacement.AGGREGATOR.consume(envelopeAddSell);


        Assertions.assertThat(InjectionReplacement.AGGREGATOR.getAggregated()).containsKeys("APPLE");
        Assertions.assertThat(InjectionReplacement.AGGREGATOR.getAggregated().get("APPLE").getBuyLevels()).hasSize(0);
        Assertions.assertThat(InjectionReplacement.AGGREGATOR.getAggregated().get("APPLE").getSellLevels()).hasSize(1);
        Assertions.assertThat(InjectionReplacement.AGGREGATOR.getAggregated().get("APPLE").getSellLevels().get(0).getOrderId()).isEqualTo(3);

        Assertions.assertThat(InjectionReplacement.AGGREGATOR.orders).isNotEmpty();
        Assertions.assertThat(InjectionReplacement.AGGREGATOR.orders).containsKeys(3L);

        final Envelope envelopeDeleteSell = InjectionReplacement.GSON.fromJson(TestConstants.INCOME_MESSAGE_CHANGE_SINGLE_SELL, Envelope.class);

        InjectionReplacement.AGGREGATOR.consume(envelopeDeleteSell);

        Assertions.assertThat(InjectionReplacement.AGGREGATOR.getAggregated()).containsKeys("APPLE");
        Assertions.assertThat(InjectionReplacement.AGGREGATOR.getAggregated().get("APPLE").getBuyLevels()).hasSize(0);
        Assertions.assertThat(InjectionReplacement.AGGREGATOR.getAggregated().get("APPLE").getSellLevels()).hasSize(1);
        Assertions.assertThat(InjectionReplacement.AGGREGATOR.getAggregated().get("APPLE").getSellLevels().get(0).getQuantity()).isEqualTo(10);
        Assertions.assertThat(InjectionReplacement.AGGREGATOR.getAggregated().get("APPLE").getSellLevels().get(0).getPrice()).isEqualByComparingTo(new BigDecimal("3.333"));

        Assertions.assertThat(InjectionReplacement.AGGREGATOR.orders).containsKeys(3L);
    }

}