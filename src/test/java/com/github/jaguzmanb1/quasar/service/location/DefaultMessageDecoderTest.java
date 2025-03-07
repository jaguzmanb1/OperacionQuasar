package com.github.jaguzmanb1.quasar.service.location;

import com.github.jaguzmanb1.quasar.service.messages.DefaultMessageDecoder;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefaultMessageDecoderTest {

    private final DefaultMessageDecoder decoder = new DefaultMessageDecoder();

    @Test
    public void testDecodeMessageValidInput() {
        List<List<String>> messages = Arrays.asList(
                Arrays.asList("this", "", "", "message"),
                Arrays.asList("", "is", "", ""),
                Arrays.asList("", "", "a", "")
        );

        String expectedMessage = "this is a message";
        String actualMessage = decoder.decodeMessage(messages);

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testDecodeMessageWithEmptyMessages() {
        List<List<String>> messages = Arrays.asList(
                Arrays.asList("", "", "", ""),
                Arrays.asList("", "", "", ""),
                Arrays.asList("", "", "", "")
        );

        String expectedMessage = "";
        String actualMessage = decoder.decodeMessage(messages);

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testDecodeMessageWithEmptyList() {
        List<List<String>> messages = Collections.emptyList();

        String expectedMessage = "";
        String actualMessage = decoder.decodeMessage(messages);

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testDecodeMessageWithSingleMessage() {
        List<List<String>> messages = Collections.singletonList(
                Arrays.asList("this", "is", "a", "message")
        );

        String expectedMessage = "this is a message";
        String actualMessage = decoder.decodeMessage(messages);

        assertEquals(expectedMessage, actualMessage);
    }
}