package me.definedoddy.engine.rendering.lighting;

import org.joml.Vector3f;

import java.awt.*;

public class Light {
    private final Vector3f position;
    private final Color colour;
    private float innerRadius = 0f;
    private float outerRadius = 10f;

    public Light(Vector3f position, Color colour) {
        this.position = position;
        this.colour = colour;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Color getColour() {
        return colour;
    }

    public void setInnerRadius(float innerRadius) {
        this.innerRadius = innerRadius;
    }

    public float getInnerRadius() {
        return innerRadius;
    }

    public void setOuterRadius(float outerRadius) {
        this.outerRadius = outerRadius;
    }

    public float getOuterRadius() {
        return outerRadius;
    }
}
