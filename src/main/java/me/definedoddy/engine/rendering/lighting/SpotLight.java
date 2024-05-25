package me.definedoddy.engine.rendering.lighting;

import org.joml.Vector3f;

import java.awt.*;

public class SpotLight extends Light {
    private Vector3f position;
    private Vector3f direction;
    private float outerRadius = 10f;
    private float innerRadius;

    public SpotLight(Vector3f position, Vector3f direction, Color colour) {
        this.position = position;
        this.direction = direction;
        this.colour = colour;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getDirection() {
        return direction;
    }

    public void setDirection(Vector3f direction) {
        this.direction = direction;
    }

    public float getOuterRadius() {
        return outerRadius;
    }

    public void setOuterRadius(float outerRadius) {
        this.outerRadius = outerRadius;
    }

    public float getInnerRadius() {
        return innerRadius;
    }

    public void setInnerRadius(float innerRadius) {
        this.innerRadius = innerRadius;
    }
}
