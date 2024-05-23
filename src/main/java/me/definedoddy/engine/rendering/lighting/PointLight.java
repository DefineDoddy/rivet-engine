package me.definedoddy.engine.rendering.lighting;

import org.joml.Vector3f;

import java.awt.*;

public class PointLight extends Light {
    private final Vector3f position;
    private float range = 10f;

    public PointLight(Vector3f position, Color colour) {
        this.position = position;
        this.colour = colour;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position.set(position);
    }

    public float getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
    }
}
