package com.github.jaguzmanb1.quasar.util;

import com.github.jaguzmanb1.quasar.exception.InvalidIntersectionException;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class TrilaterationUtilsTest {

    @Test
    void testIsValidTrilateration_Valid() {
        Point P1 = new Point(0, 0);
        Point P2 = new Point(1, 1);
        Point P3 = new Point(2, 2);
        double r1 = 1.5;
        double r2 = 1.5;
        double r3 = 1.5;

        assertTrue(TrilaterationUtils.isValidTrilateration(P1, r1, P2, r2, P3, r3));
    }

    @Test
    void testIsValidTrilateration_Invalid() {
        Point P1 = new Point(0, 0);
        Point P2 = new Point(10, 10);
        Point P3 = new Point(20, 20);
        double r1 = 1.5;
        double r2 = 1.5;
        double r3 = 1.5;

        assertFalse(TrilaterationUtils.isValidTrilateration(P1, r1, P2, r2, P3, r3));
    }

    @Test
    void testTrilaterate_Success() {
        Point P1 = new Point(-1, 0);
        Point P2 = new Point(1, 0);
        Point P3 = new Point(0, 1);
        double r1 = 1.0;
        double r2 = 1.0;
        double r3 = 1.0;

        Point expectedPoint = new Point(0, 0);

        Point result = TrilaterationUtils.trilaterate(P1, r1, P2, r2, P3, r3);

        assertEquals(expectedPoint, result);
    }

    @Test
    void testTrilaterate_InvalidIntersection() {
        Point P1 = new Point(0, 0);
        Point P2 = new Point(10, 10);
        Point P3 = new Point(20, 20);
        double r1 = 1.5;
        double r2 = 1.5;
        double r3 = 1.5;

        assertThrows(InvalidIntersectionException.class, () ->
                TrilaterationUtils.trilaterate(P1, r1, P2, r2, P3, r3));
    }
}