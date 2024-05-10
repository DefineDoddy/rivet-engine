package me.definedoddy.engine.rendering.lighting;

import org.joml.Vector3f;

import java.awt.*;

public class Light {
    private final Vector3f position;
    private final Color colour;

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
}
