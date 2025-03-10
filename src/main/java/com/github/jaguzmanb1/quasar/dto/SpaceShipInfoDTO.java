package com.github.jaguzmanb1.quasar.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * Data Transfer Object (DTO) representing information about a spaceship.
 * This DTO is used to store and validate satellite data for processing.
 */
public class SpaceShipInfoDTO {

    /**
     * Optional name for the request.
     */
    private String name;

    /**
     * Distance from the spaceship to a reference point. Cannot be null.
     */
    @NotNull(message = "Distance cannot be null")
    private Double distance;

    /**
     * List of messages received from the spaceship. Cannot be empty.
     */
    @NotEmpty(message = "Message list cannot be empty")
    private List<String> message;

    /**
     * Default constructor.
     */
    public SpaceShipInfoDTO() {}

    /**
     * Parameterized constructor to initialize spaceship information.
     *
     * @param name the name of the spaceship
     * @param distance the distance from the spaceship to the reference point
     * @param message the list of messages received from the spaceship
     */
    public SpaceShipInfoDTO(String name, double distance, List<String> message) {
        this.name = name;
        this.distance = distance;
        this.message = message;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Gets the distance from the spaceship to the reference point.
     *
     * @return the distance value
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Sets the distance from the spaceship to the reference point.
     *
     * @param distance the new distance value
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }

    /**
     * Gets the list of messages received from the spaceship.
     *
     * @return the list of messages
     */
    public List<String> getMessage() {
        return message;
    }

    public String getMessageAsString() {
        return convertListToString(message);
    }

    private String convertListToString(List<String> messages) {
        return (messages != null && !messages.isEmpty()) ? String.join(",", messages) : null;
    }

    /**
     * Sets the list of messages received from the spaceship.
     *
     * @param message the new list of messages
     */
    public void setMessage(List<String> message) {
        this.message = message;
    }
}
