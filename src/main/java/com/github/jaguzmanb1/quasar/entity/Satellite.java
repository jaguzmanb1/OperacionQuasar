package com.github.jaguzmanb1.quasar.entity;

import java.awt.*;

/**
 * Entity representing a satellite in the system.
 * This class stores the satellite's name and its fixed position in space.
 */
public class Satellite {

    /**
     * The name of the satellite.
     */
    private String name;

    /**
     * The fixed position of the satellite in a two-dimensional space.
     */
    private Point position;

    /**
     * Constructor to initialize a satellite with a name and position.
     *
     * @param pName the name of the satellite
     * @param pPosition the position of the satellite as a Point object
     */
    public Satellite(String pName, Point pPosition) {
        this.setName(pName);
        this.setPosition(pPosition);
    }

    /**
     * Gets the name of the satellite.
     *
     * @return the satellite's name as a String
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the satellite.
     *
     * @param name the new name of the satellite
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the position of the satellite.
     *
     * @return the satellite's position as a Point object
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Sets the position of the satellite.
     *
     * @param position the new position of the satellite
     */
    public void setPosition(Point position) {
        this.position = position;
    }
}
