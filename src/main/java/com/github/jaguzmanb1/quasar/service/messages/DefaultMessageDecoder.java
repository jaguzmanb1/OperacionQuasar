package com.github.jaguzmanb1.quasar.service.messages;

import java.util.List;
import java.util.stream.IntStream;

/**
 * Default implementation of the MessageDecoderInterface.
 * This class reconstructs a message by merging fragmented words received from multiple sources.
 */
public class DefaultMessageDecoder implements MessageDecoderInterface {

    /**
     * Decodes a fragmented message from multiple sources.
     * It iterates through the message fragments and reconstructs the final message
     * by selecting the first non-empty word at each index position.
     *
     * @param messages A list of lists, where each inner list represents a message fragment received from a source.
     * @return The reconstructed message as a single string.
     */
    @Override
    public String decodeMessage(List<List<String>> messages) {
        int maxLength = messages.stream()
                .mapToInt(List::size)
                .max()
                .orElse(0);

        return IntStream.range(0, maxLength)
                .mapToObj(i -> messages.stream()
                        .filter(a -> i < a.size() && !a.get(i).isEmpty()) // Select the first non-empty element at index i
                        .findFirst()
                        .map(a -> a.get(i))
                        .orElse("")) // Default to empty string if no valid word is found
                .filter(s -> !s.isEmpty()) // Remove empty words
                .reduce((a, b) -> a + " " + b) // Concatenate words with spaces
                .orElse("");
    }
}