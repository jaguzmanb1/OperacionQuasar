package com.github.jaguzmanb1.quasar.service;

import com.github.jaguzmanb1.quasar.entity.Satellite;
import com.github.jaguzmanb1.quasar.util.MergeArraysUtils;
import com.github.jaguzmanb1.quasar.util.TrilaterationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TranslationServiceTest {
    private Satellite[] satellites;
    private TranslationService translationService;

    @BeforeEach
    void setUp() {
        satellites = new Satellite[]{
                new Satellite("Sat1", new Point(0, 0)),
                new Satellite("Sat2", new Point(1, 1)),
                new Satellite("Sat3", new Point(2, 2))
        };
        translationService = new TranslationService(satellites);
    }

    @Test
    void testConstructor_Success() {
        Satellite[] satellites = new Satellite[]{
                new Satellite("Sat1", new Point(0, 0)),
                new Satellite("Sat2", new Point(1, 1)),
                new Satellite("Sat3", new Point(2, 2))
        };

        TranslationService translationService = new TranslationService(satellites);

        assertNotNull(translationService);
    }

    @Test
    void testConstructor_InsufficientSatellites() {
        Satellite[] satellites = new Satellite[]{
                new Satellite("Sat1", new Point(0, 0)),
                new Satellite("Sat2", new Point(1, 1))
        };

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new TranslationService(satellites));

        assertEquals("At least 3 satellites are required", exception.getMessage());
    }

    @Test
    void testGetLocation_Success() {
        double[] distances = {1.0, 1.5, 2.0};
        Point expectedPoint = new Point(3, 3);

        try (MockedStatic<TrilaterationUtils> mockedTrilateration = mockStatic(TrilaterationUtils.class)) {
            mockedTrilateration.when(() ->
                    TrilaterationUtils.trilaterate(
                            satellites[0].getPosition(),
                            distances[0],
                            satellites[1].getPosition(),
                            distances[1],
                            satellites[2].getPosition(),
                            distances[2])
            ).thenReturn(expectedPoint);

            Point result = translationService.getLocation(distances);

            assertEquals(expectedPoint, result);
        }
    }

    @Test
    void testGetLocation_InvalidDistances() {
        double[] invalidDistances = {1.0, 2.0};
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                translationService.getLocation(invalidDistances));

        assertEquals("Exactly 3 distances are required", exception.getMessage());
    }

    @Test
    void testGetMessage_Success() {
        String[][] messages = {
                {"", "hello", "world"},
                {"hello", "", "world"},
                {"hello", "world", ""}
        };
        String expectedMessage = "hello world";

        try (MockedStatic<MergeArraysUtils> mockedMergeArrays = mockStatic(MergeArraysUtils.class)) {
            mockedMergeArrays.when(() -> MergeArraysUtils.mergeArrays(messages)).thenReturn(expectedMessage);

            String result = translationService.getMessage(messages);

            assertEquals(expectedMessage, result);
        }
    }
}