package me.definedoddy.engine.rendering.lighting;

import java.awt.*;

public abstract class Light {
    protected Color colour = Color.WHITE;
    protected float intensity = 1f;

    public Color getColour() {
        return colour;
    }

    public void setColour(Color colour) {
        this.colour = colour;
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }
}
