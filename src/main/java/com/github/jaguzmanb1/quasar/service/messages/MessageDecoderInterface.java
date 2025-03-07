package com.github.jaguzmanb1.quasar.service.messages;

import java.util.List;

/**
 * Interface for decoding messages from multiple sources.
 * Implementations of this interface should provide different strategies
 * to reconstruct a coherent message from fragmented data.
 */
public interface MessageDecoderInterface {

    /**
     * Decodes a fragmented message received from multiple sources.
     * The method reconstructs the original message by merging different message fragments.
     *
     * @param messages A list of lists, where each inner list represents a sequence of words
     *                 received from a different source.
     * @return The reconstructed message as a single string.
     * @throws IllegalArgumentException if the input data is invalid or empty.
     */
    String decodeMessage(List<List<String>> messages);
}
