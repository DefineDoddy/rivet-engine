package me.definedoddy.engine.rendering.lighting;

import org.joml.Vector3f;

import java.awt.*;

public class Light {
    private final Vector3f position;
    private final Color colour;
    private final float attenuation;

    public Light(Vector3f position, Color colour) {
        this(position, colour, 10);
    }

    public Light(Vector3f position, Color colour, float attenuation) {
        this.position = position;
        this.colour = colour;
        this.attenuation = attenuation;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Color getColour() {
        return colour;
    }

    public float getAttenuation() {
        return attenuation;
    }
}
