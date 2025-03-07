package com.github.jaguzmanb1.quasar.service.location;

import com.github.jaguzmanb1.quasar.exception.InvalidIntersectionException;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class TrilaterationCalculatorTest {

    private final TrilaterationCalculator calculator = new TrilaterationCalculator();

    @Test
    public void testCalculateLocationValidInput() {
        Point P1 = new Point(1, 0);
        Point P2 = new Point(-1, 0);
        Point P3 = new Point(0, 1);
        double r1 = 1;
        double r2 = 1;
        double r3 = 1;

        Point expectedLocation = new Point(0, 0);
        Point actualLocation = calculator.calculateLocation(P1, r1, P2, r2, P3, r3);

        assertEquals(expectedLocation, actualLocation);
    }

    @Test
    public void testCalculateLocationInvalidInput() {
        Point P1 = new Point(-500, -200);
        Point P2 = new Point(100, -100);
        Point P3 = new Point(500, 100);
        double r1 = 100.0;
        double r2 = 100.0;
        double r3 = 100.0;

        assertThrows(InvalidIntersectionException.class, () -> {
            calculator.calculateLocation(P1, r1, P2, r2, P3, r3);
        });
    }
}