package org.nazymko.storage;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.nazymko.InjectionReplacement;
import org.nazymko.messages.model.in.Envelope;

import static org.junit.Assert.*;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class AggregatorTest {
    @Test
    public void consume() throws Exception {
        final Envelope envelope = InjectionReplacement.GSON.fromJson(TestConstants.INCOME_MESSAGE_SINGLE_SELL, Envelope.class);

        Assertions.assertThat(envelope.getInSequenceNumber()).isEqualTo(2);
        Assertions.assertThat(envelope.getMessages()).hasSize(1);

        InjectionReplacement.AGGREGATOR.consume(envelope);


        System.out.println("envelope = " + envelope);
    }

}