package com.github.jaguzmanb1.quasar.service.location;

import com.github.jaguzmanb1.quasar.exception.InvalidIntersectionException;
import java.awt.*;

/**
 * Implementation of {@link LocationCalculatorInterface} using trilateration.
 * This class calculates the position of an object based on distances from three known points (satellites).
 */
public class TrilaterationCalculator implements LocationCalculatorInterface {

    /**
     * Calculates the position of an object using trilateration.
     * If the given distances and points do not form a valid trilateration, an exception is thrown.
     *
     * @param P1 First point (satellite position)
     * @param r1 Distance from the first point to the unknown position
     * @param P2 Second point (satellite position)
     * @param r2 Distance from the second point to the unknown position
     * @param P3 Third point (satellite position)
     * @param r3 Distance from the third point to the unknown position
     * @return The calculated position as a {@link Point} object
     * @throws InvalidIntersectionException if trilateration conditions are not met
     */
    @Override
    public Point calculateLocation(Point P1, double r1, Point P2, double r2, Point P3, double r3) {
        if (!isValidTrilateration(P1, r1, P2, r2, P3, r3)) {
            throw new InvalidIntersectionException("Error 32792 - Indeterminate position...");
        }

        double d = P1.distance(P2); // Distance between P1 and P2

        // Unit vector in the direction of P2 from P1
        double exX = (P2.getX() - P1.getX()) / d;
        double exY = (P2.getY() - P1.getY()) / d;

        // Projection of P3 onto the line defined by P1 and P2
        double i = exX * (P3.getX() - P1.getX()) + exY * (P3.getY() - P1.getY());

        // Unit vector perpendicular to the line P1-P2
        double eyX = (P3.getX() - P1.getX() - i * exX);
        double eyY = (P3.getY() - P1.getY() - i * exY);
        double eyNorm = Math.sqrt(eyX * eyX + eyY * eyY);
        eyX /= eyNorm;
        eyY /= eyNorm;

        // Projection of P3 onto the perpendicular axis
        double j = eyX * (P3.getX() - P1.getX()) + eyY * (P3.getY() - P1.getY());

        // Calculate x coordinate in transformed space
        double x = (Math.pow(r1, 2) - Math.pow(r2, 2) + Math.pow(d, 2)) / (2 * d);

        // Calculate y coordinate in transformed space
        double y = (Math.pow(r1, 2) - Math.pow(r3, 2) + Math.pow(i, 2) + Math.pow(j, 2)) / (2 * j) - (i * x / j);

        // Convert back to original coordinate system
        int finalX = (int) Math.round(P1.getX() + x * exX + y * eyX);
        int finalY = (int) Math.round(P1.getY() + x * exY + y * eyY);

        return new Point(finalX, finalY);
    }

    /**
     * Validates whether the given points and distances form a valid trilateration.
     * Ensures that the three circles defined by their centers and radii intersect in a single point.
     *
     * @param P1 First point (satellite position)
     * @param r1 Distance from the first point
     * @param P2 Second point (satellite position)
     * @param r2 Distance from the second point
     * @param P3 Third point (satellite position)
     * @param r3 Distance from the third point
     * @return {@code true} if trilateration is possible, {@code false} otherwise
     */
    private boolean isValidTrilateration(Point P1, double r1, Point P2, double r2, Point P3, double r3) {
        double d12 = P1.distance(P2);
        double d13 = P1.distance(P3);
        double d23 = P2.distance(P3);
        return (d12 <= r1 + r2 && d12 >= Math.abs(r1 - r2)) &&
                (d13 <= r1 + r3 && d13 >= Math.abs(r1 - r3)) &&
                (d23 <= r2 + r3 && d23 >= Math.abs(r2 - r3));
    }
}
