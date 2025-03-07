package com.github.jaguzmanb1.quasar.service.location;

import java.awt.*;

/**
 * Interface for calculating the location of an object based on distances from known reference points.
 * Implementations of this interface should provide different strategies for determining the object's position.
 */
public interface LocationCalculatorInterface {

    /**
     * Calculates the position of an object given three reference points and their respective distances.
     *
     * @param P1 First reference point
     * @param r1 Distance from the first reference point to the object
     * @param P2 Second reference point
     * @param r2 Distance from the second reference point to the object
     * @param P3 Third reference point
     * @param r3 Distance from the third reference point to the object
     * @return The calculated position as a {@link Point} object
     * @throws IllegalArgumentException if the input data does not allow a valid calculation
     */
    Point calculateLocation(Point P1, double r1, Point P2, double r2, Point P3, double r3);
}
