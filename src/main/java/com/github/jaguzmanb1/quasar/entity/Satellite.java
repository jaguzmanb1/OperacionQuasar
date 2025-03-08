package com.github.jaguzmanb1.quasar.entity;

import jakarta.persistence.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a satellite entity in the database.
 */
@Entity
@Table(name = "satellites")
public class Satellite {

    /**
     * Unique identifier for the satellite.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the satellite. It must be unique and cannot be null.
     */
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * X coordinate of the satellite's position.
     */
    @Column(name = "position_x", nullable = false)
    private int positionX;

    /**
     * Y coordinate of the satellite's position.
     */
    @Column(name = "position_y", nullable = false)
    private int positionY;

    /**
     * Distance from the satellite to the target.
     */
    @Column(nullable = false)
    private Double distance;

    /**
     * Message received by the satellite, stored as a comma-separated string.
     */
    @Column(name = "received_message")
    private String receivedMessage;

    /**
     * Default constructor.
     */
    public Satellite() {}

    /**
     * Constructs a Satellite object with a name, position, distance, and received message.
     *
     * @param name The name of the satellite.
     * @param position The position of the satellite as a Point.
     * @param distance The distance from the satellite to the target.
     * @param receivedMessage The message received by the satellite, represented as a list of strings.
     */
    public Satellite(String name, Point position, Double distance, List<String> receivedMessage) {
        this.name = name;
        this.positionX = position.x;
        this.positionY = position.y;
        this.distance = distance;
        this.receivedMessage = receivedMessage != null ? String.join(",", receivedMessage) : null;
    }

    /**
     * Constructs a Satellite object with a name and position. Distance is initialized to 0 and message to an empty string.
     *
     * @param name The name of the satellite.
     * @param position The position of the satellite as a Point.
     */
    public Satellite(String name, Point position) {
        this.name = name;
        this.positionX = position.x;
        this.positionY = position.y;
        this.distance = 0.0;
        this.receivedMessage = "";
    }

    /**
     * Gets the name of the satellite.
     *
     * @return The name of the satellite.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the satellite.
     *
     * @param name The new name of the satellite.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the position of the satellite as a Point.
     *
     * @return The position of the satellite.
     */
    public Point getPosition() {
        return new Point(positionX, positionY);
    }

    /**
     * Sets the position of the satellite.
     *
     * @param position The new position of the satellite as a Point.
     */
    public void setPosition(Point position) {
        this.positionX = position.x;
        this.positionY = position.y;
    }

    /**
     * Gets the distance from the satellite to the target.
     *
     * @return The distance value.
     */
    public Double getDistance() {
        return distance;
    }

    /**
     * Sets the distance from the satellite to the target.
     *
     * @param distance The new distance value.
     */
    public void setDistance(Double distance) {
        this.distance = distance;
    }

    /**
     * Gets the received message as a list of strings.
     *
     * @return The received message split into a list, or null if no message was received.
     */
    public List<String> getReceivedMessage() {
        return receivedMessage != null ? Arrays.asList(receivedMessage.split(",")) : null;
    }

    /**
     * Sets the received message as a list of strings.
     *
     * @param receivedMessage The received message represented as a list of strings.
     */
    public void setReceivedMessage(List<String> receivedMessage) {
        this.receivedMessage = receivedMessage != null ? String.join(",", receivedMessage) : null;
    }
}