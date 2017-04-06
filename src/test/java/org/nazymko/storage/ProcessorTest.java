package org.nazymko.storage;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.nazymko.InjectionReplacement;
import org.nazymko.messages.model.in.Envelope;
import org.nazymko.messages.model.in.Message;

import java.math.BigDecimal;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class ProcessorTest {
    @Test
    public void parseTest() {
        Envelope envelope = InjectionReplacement.GSON.fromJson(TestConstants.INCOME_MESSAGE, Envelope.class);


        Assertions.assertThat(envelope.getInSequenceNumber()).isEqualTo(1);
        Assertions.assertThat(envelope.getMessages()).hasSize(4);
        Assertions.assertThat(envelope.getMessages().get(0).getType()).isEqualTo(Message.Type.addOrder);
        Assertions.assertThat(envelope.getMessages().get(0).getOrderId()).isEqualTo(3);
        Assertions.assertThat(envelope.getMessages().get(0).getProductId()).isEqualTo("APPLE");
        Assertions.assertThat(envelope.getMessages().get(0).getSide()).isEqualTo(Message.Side.buy);
        Assertions.assertThat(envelope.getMessages().get(0).getPrice()).isEqualByComparingTo(BigDecimal.ONE);

    }


    @Test(timeout = 3000)
    public void testConcurrentQueue() throws Exception {


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                InjectionReplacement.AGGREGATOR.addRaw("test");
                InjectionReplacement.AGGREGATOR.addRaw("test2");

            }
        }).start();


        System.out.println("Going to take item");
        MetricsHolder.MeasurementObject take = InjectionReplacement.AGGREGATOR.getRawStorage().take();
        System.out.println("Taken : " + take.getRaw());

        Assertions.assertThat(take).isEqualTo("test");

    }
}