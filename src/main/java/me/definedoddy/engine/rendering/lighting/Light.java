package me.definedoddy.engine.rendering.lighting;

import org.joml.Vector3f;

import java.awt.*;

public class Light {
    private final Vector3f position;
    private final Color colour;
    private final Vector3f attenuation;

    public Light(Vector3f position, Color colour) {
        this.position = position;
        this.colour = colour;
        this.attenuation = new Vector3f(1, 0, 0);
    }

    public Vector3f getPosition() {
        return position;
    }

    public Color getColour() {
        return colour;
    }

    public Vector3f getAttenuation() {
        return attenuation;
    }

    public void setAttenuation(float constant, float linear, float exponent) {
        attenuation.set(constant, linear, exponent);
    }
}
