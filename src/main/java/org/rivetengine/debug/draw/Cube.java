package org.rivetengine.debug.draw;

import org.joml.Vector3f;

import java.awt.*;

public class Cube {
    private final Vector3f position;
    private final Vector3f size;

    private Color colour = Color.WHITE;
    private float width = 1.0f;

    public Cube(Vector3f position, Vector3f size) {
        this.position = position;
        this.size = size;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getSize() {
        return size;
    }

    public Cube setColour(Color colour) {
        this.colour = colour;
        return this;
    }

    public Color getColour() {
        return colour;
    }

    public Cube setWidth(float width) {
        this.width = width;
        return this;
    }

    public float getWidth() {
        return width;
    }

    public void draw() {
        float x = size.x / 2 + position.x;
        float y = size.y / 2 + position.y;
        float z = size.z / 2 + position.z;

        float x2 = -size.x / 2 + position.x;
        float y2 = -size.y / 2 + position.y;
        float z2 = -size.z / 2 + position.z;

        // Front
        new Line(new Vector3f(x, y, z), new Vector3f(x2, y, z)).setColour(colour).setWidth(width).draw();
        new Line(new Vector3f(x2, y, z), new Vector3f(x2, y2, z)).setColour(colour).setWidth(width).draw();
        new Line(new Vector3f(x2, y2, z), new Vector3f(x, y2, z)).setColour(colour).setWidth(width).draw();
        new Line(new Vector3f(x, y2, z), new Vector3f(x, y, z)).setColour(colour).setWidth(width).draw();

        // Back
        new Line(new Vector3f(x, y, z2), new Vector3f(x2, y, z2)).setColour(colour).setWidth(width).draw();
        new Line(new Vector3f(x2, y, z2), new Vector3f(x2, y2, z2)).setColour(colour).setWidth(width).draw();
        new Line(new Vector3f(x2, y2, z2), new Vector3f(x, y2, z2)).setColour(colour).setWidth(width).draw();
        new Line(new Vector3f(x, y2, z2), new Vector3f(x, y, z2)).setColour(colour).setWidth(width).draw();

        // Sides
        new Line(new Vector3f(x, y, z), new Vector3f(x, y, z2)).setColour(colour).setWidth(width).draw();
        new Line(new Vector3f(x2, y, z), new Vector3f(x2, y, z2)).setColour(colour).setWidth(width).draw();
        new Line(new Vector3f(x2, y2, z), new Vector3f(x2, y2, z2)).setColour(colour).setWidth(width).draw();
        new Line(new Vector3f(x, y2, z), new Vector3f(x, y2, z2)).setColour(colour).setWidth(width).draw();
    }
}
