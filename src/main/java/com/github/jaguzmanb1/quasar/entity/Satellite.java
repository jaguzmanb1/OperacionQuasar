package com.github.jaguzmanb1.quasar.entity;

import jakarta.persistence.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "satellites")
public class Satellite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Transient
    private Point position;

    @Column(nullable = false)
    private Double distance;

    /**
     * The received message fragments from the satellite, stored as a single string (comma-separated).
     */
    @Column(name = "received_message")
    private String receivedMessage;

    public Satellite() {}

    public Satellite(String name, Point position, Double distance, List<String> receivedMessage) {
        this.name = name;
        this.position = position;
        this.distance = distance;
        this.receivedMessage = receivedMessage != null ? String.join(",", receivedMessage) : null;
    }

    public Satellite(String name, Point position) {
        this.name = name;
        this.position = position;
        this.distance = 0.0;
        this.receivedMessage = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public List<String> getReceivedMessage() {
        return receivedMessage != null ? Arrays.asList(receivedMessage.split(",")) : null;
    }

    public void setReceivedMessage(List<String> receivedMessage) {
        this.receivedMessage = receivedMessage != null ? String.join(",", receivedMessage) : null;
    }
}