package me.definedoddy.engine.rendering.lighting;

import java.awt.*;

public abstract class Light {
    protected Color colour = Color.WHITE;

    public Color getColour() {
        return colour;
    }

    public void setColour(Color colour) {
        this.colour = colour;
    }
}
