package me.definedoddy.toolkit.debug.draw;

import org.joml.Vector3f;

import java.awt.*;

public class Line {
    private final Vector3f start;
    private final Vector3f end;

    private Color color = Color.WHITE;
    private float width = 1.0f;

    public Line(Vector3f start, Vector3f end) {
        this.start = start;
        this.end = end;
    }

    public Vector3f getStart() {
        return start;
    }

    public Vector3f getEnd() {
        return end;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }
}
