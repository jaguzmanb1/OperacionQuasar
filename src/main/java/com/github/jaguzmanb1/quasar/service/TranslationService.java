package com.github.jaguzmanb1.quasar.service;

import com.github.jaguzmanb1.quasar.entity.Satellite;
import static com.github.jaguzmanb1.quasar.util.MergeArraysUtils.mergeArrays;
import static com.github.jaguzmanb1.quasar.util.TrilaterationUtils.trilaterate;
import java.awt.*;

/**
 * Service for translating satellite data into location and message.
 */
public class TranslationService {
    private static final int REQUIRED_SATELLITES = 3;
    private final Satellite[] satellites;

    public TranslationService(Satellite[] satellites) {
        validateSatelliteCount(satellites);
        this.satellites = satellites;
    }

    /**
     * Gets the location based on the distances from the satellites.
     *
     * @param distances Array of distances from the satellites
     * @return The calculated location
     */
    public Point getLocation(double[] distances) {
        validateDistanceCount(distances);

        return trilaterate(
                this.satellites[0].getPosition(),
                distances[0],
                this.satellites[1].getPosition(),
                distances[1],
                this.satellites[2].getPosition(),
                distances[2]);
    }

    /**
     * Merges the given messages into a single message.
     *
     * @param messages Array of message arrays
     * @return The merged message
     */
    public String getMessage(String[][] messages) {
        return mergeArrays(messages);
    }

    private void validateSatelliteCount(Satellite[] satellites) {
        if (satellites.length < REQUIRED_SATELLITES) {
            throw new IllegalArgumentException("At least " + REQUIRED_SATELLITES + " satellites are required");
        }
    }

    private void validateDistanceCount(double[] distances) {
        if (distances.length != REQUIRED_SATELLITES) {
            throw new IllegalArgumentException("Exactly " + REQUIRED_SATELLITES + " distances are required");
        }
    }
}