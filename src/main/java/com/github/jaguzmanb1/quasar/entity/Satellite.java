package com.github.jaguzmanb1.quasar.entity;

import java.awt.*;

public class Satellite {
    private String name;
    private Point position;

    public Satellite(String pName, Point pPosition) {
        this.setName(pName);
        this.setPosition(pPosition);
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
}
