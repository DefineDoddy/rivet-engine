package me.definedoddy.engine.rendering.lighting;

import org.joml.Vector3f;

import java.awt.*;

public class DirectionalLight extends Light {
    private Vector3f direction;

    public DirectionalLight(Vector3f direction, Color colour) {
        this.direction = direction;
        this.colour = colour;
        this.intensity = 0.5f;
    }

    public Vector3f getDirection() {
        return direction;
    }

    public void setDirection(Vector3f direction) {
        this.direction = direction;
    }
}
