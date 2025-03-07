package com.github.jaguzmanb1.quasar.util;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Utility class for merging arrays.
 */
public class MergeArraysUtils {

    private MergeArraysUtils() {}

    /**
     * Merges the given arrays into a single string.
     *
     * @param arrays Arrays to merge
     * @return The merged string
     */
    public static String mergeArrays(String[]... arrays) {
        int maxLength = Arrays.stream(arrays).mapToInt(a -> a.length).max().orElse(0);

        return IntStream.range(0, maxLength)
                .mapToObj(i -> Arrays.stream(arrays)
                        .filter(a -> i < a.length && !a[i].isEmpty())
                        .findFirst()
                        .map(a -> a[i])
                        .orElse(""))
                .filter(s -> !s.isEmpty())
                .reduce((a, b) -> a + " " + b)
                .orElse("");
    }
}