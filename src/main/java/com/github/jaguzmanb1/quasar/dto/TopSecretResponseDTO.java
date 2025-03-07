package com.github.jaguzmanb1.quasar.dto;

import java.awt.*;

/**
 * Data Transfer Object (DTO) representing the response for a top-secret request.
 * This DTO contains the calculated position and the reconstructed message.
 */
public class TopSecretResponseDTO {

    /**
     * The calculated position based on trilateration.
     */
    private Point position;

    /**
     * The reconstructed message from satellite transmissions.
     */
    private String message;

    /**
     * Constructor to initialize the response DTO with position and message.
     *
     * @param position the calculated position of the source
     * @param message the reconstructed message
     */
    public TopSecretResponseDTO(Point position, String message) {
        this.position = position;
        this.message = message;
    }

    /**
     * Gets the calculated position.
     *
     * @return the position as a Point object
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Sets the calculated position.
     *
     * @param position the new position to be set
     */
    public void setPosition(Point position) {
        this.position = position;
    }

    /**
     * Gets the reconstructed message.
     *
     * @return the message as a String
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the reconstructed message.
     *
     * @param message the new message to be set
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
