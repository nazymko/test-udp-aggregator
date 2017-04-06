package org.nazymko.storage;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.nazymko.InjectionReplacement;
import org.nazymko.messages.model.in.Envelope;
import org.nazymko.messages.model.in.Message;

import static org.junit.Assert.*;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class MessageSorterTest {
    @Test
    public void compare() throws Exception {
        final Envelope envelope = InjectionReplacement.GSON.fromJson(TestConstants.INCOME_ENVELOPE, Envelope.class);

        envelope.getMessages().sort(new MessageSorter());

        Assertions.assertThat(envelope.getMessages()).hasSize(2);
        Assertions.assertThat(envelope.getMessages().get(0).getType()).isEqualTo(Message.Type.changeOrder);
        Assertions.assertThat(envelope.getMessages().get(1).getType()).isEqualTo(Message.Type.deleteOrder);

    }

}