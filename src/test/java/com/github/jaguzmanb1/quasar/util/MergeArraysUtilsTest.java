package com.github.jaguzmanb1.quasar.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MergeArraysUtilsTest {

    @Test
    void testMergeArrays_Success() {
        String[][] messages = {
                {"", "hello", "world"},
                {"hello", "", "world"},
                {"hello", "world", ""}
        };
        String expectedMessage = "hello hello world";

        String result = MergeArraysUtils.mergeArrays(messages);

        assertEquals(expectedMessage, result);
    }

    @Test
    void testMergeArrays_EmptyArrays() {
        String[][] messages = {
                {},
                {},
                {}
        };
        String expectedMessage = "";

        String result = MergeArraysUtils.mergeArrays(messages);

        assertEquals(expectedMessage, result);
    }

    @Test
    void testMergeArrays_SingleArray() {
        String[][] messages = {
                {"hello", "world"}
        };
        String expectedMessage = "hello world";

        String result = MergeArraysUtils.mergeArrays(messages);

        assertEquals(expectedMessage, result);
    }

    @Test
    void testMergeArrays_MixedEmptyAndNonEmpty() {
        String[][] messages = {
                {"", "", "world"},
                {"hello", "", ""},
                {"", "world", ""}
        };
        String expectedMessage = "hello world world";

        String result = MergeArraysUtils.mergeArrays(messages);

        assertEquals(expectedMessage, result);
    }
}