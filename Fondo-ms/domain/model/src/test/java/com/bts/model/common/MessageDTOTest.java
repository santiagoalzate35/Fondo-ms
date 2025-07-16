package com.bts.model.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MessageDTOTest {
    @Test
    public void testMessageDTO() {
        MessageDTO<String> message = new MessageDTO<String>("Message test");
        Assertions.assertEquals("Message test", message.message());
    }
}