package com.github.jaguzmanb1.quasar.util;

import com.github.jaguzmanb1.quasar.exception.InvalidIntersectionException;

import java.awt.*;

/**
 * Utility class for trilateration calculations.
 */
public class TrilaterationUtils {

    private TrilaterationUtils() {}


    /**
     * Validates if the given points and radii can form a valid trilateration.
     *
     * @param P1 First point
     * @param r1 Radius of the first point
     * @param P2 Second point
     * @param r2 Radius of the second point
     * @param P3 Third point
     * @param r3 Radius of the third point
     * @return true if valid trilateration, false otherwise
     */
    public static boolean isValidTrilateration(Point P1, double r1, Point P2, double r2, Point P3, double r3) {
        double d12 = P1.distance(P2);
        double d13 = P1.distance(P3);
        double d23 = P2.distance(P3);

        return (d12 <= r1 + r2 && d12 >= Math.abs(r1 - r2)) &&
                (d13 <= r1 + r3 && d13 >= Math.abs(r1 - r3)) &&
                (d23 <= r2 + r3 && d23 >= Math.abs(r2 - r3));
    }

    /**
     * Calculates the intersection point of three circles defined by the given points and radii.
     *
     * @param P1 First point
     * @param r1 Radius of the first point
     * @param P2 Second point
     * @param r2 Radius of the second point
     * @param P3 Third point
     * @param r3 Radius of the third point
     * @return The intersection point
     * @throws InvalidIntersectionException if the points and radii do not form a valid trilateration
     */
    public static Point trilaterate(Point P1, double r1, Point P2, double r2, Point P3, double r3) {
        if (!isValidTrilateration(P1, r1, P2, r2, P3, r3)) {
            throw new InvalidIntersectionException("Error 32792 - Posición indeterminada - La interferencia del campo de asteroides ha corrompido la señal. Triangulación fallida, posible trampa imperial o señal fantasma. Reintentar con datos válidos o proceder con extrema cautela.");
        }

        double d = Math.sqrt(Math.pow(P2.getX() - P1.getX(), 2) + Math.pow(P2.getY() - P1.getY(), 2));
        double exX = (P2.getX() - P1.getX()) / d;
        double exY = (P2.getY() - P1.getY()) / d;

        double i = exX * (P3.getX() - P1.getX()) + exY * (P3.getY() - P1.getY());

        double eyX = (P3.getX() - P1.getX() - i * exX);
        double eyY = (P3.getY() - P1.getY() - i * exY);
        double eyNorm = Math.sqrt(eyX * eyX + eyY * eyY);
        eyX /= eyNorm;
        eyY /= eyNorm;

        double j = eyX * (P3.getX() - P1.getX()) + eyY * (P3.getY() - P1.getY());

        double x = (Math.pow(r1, 2) - Math.pow(r2, 2) + Math.pow(d, 2)) / (2 * d);

        double y = (Math.pow(r1, 2) - Math.pow(r3, 2) + Math.pow(i, 2) + Math.pow(j, 2)) / (2 * j) - (i * x / j);

        int finalX = (int) Math.round(P1.getX() + x * exX + y * eyX);
        int finalY = (int) Math.round(P1.getY() + x * exY + y * eyY);

        return new Point(finalX, finalY);
    }
}