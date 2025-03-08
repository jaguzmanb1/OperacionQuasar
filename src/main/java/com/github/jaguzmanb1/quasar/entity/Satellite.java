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

    // Guardar las coordenadas como columnas separadas
    @Column(name = "position_x", nullable = false)
    private int positionX;

    @Column(name = "position_y", nullable = false)
    private int positionY;

    @Column(nullable = false)
    private Double distance;

    @Column(name = "received_message")
    private String receivedMessage;

    public Satellite() {}

    public Satellite(String name, Point position, Double distance, List<String> receivedMessage) {
        this.name = name;
        this.positionX = position.x;
        this.positionY = position.y;
        this.distance = distance;
        this.receivedMessage = receivedMessage != null ? String.join(",", receivedMessage) : null;
    }

    public Satellite(String name, Point position) {
        this.name = name;
        this.positionX = position.x;
        this.positionY = position.y;
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
        return new Point(positionX, positionY);
    }

    public void setPosition(Point position) {
        this.positionX = position.x;
        this.positionY = position.y;
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
