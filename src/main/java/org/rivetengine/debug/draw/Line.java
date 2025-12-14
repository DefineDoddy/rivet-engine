package org.rivetengine.debug.draw;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class Line {
    public final Vector3f start;
    public final Vector3f end;

    public Color colour = Color.WHITE;
    public float width = 1.0f;

    public Line(Vector3f start, Vector3f end) {
        this.start = start;
        this.end = end;
    }

    public Line setColour(Color colour) {
        this.colour = colour;
        return this;
    }

    public Line setWidth(float width) {
        this.width = width;
        return this;
    }

    public void draw() {
        GL11.glLineWidth(width);
        GL11.glColor3f(colour.getRed() / 255f, colour.getGreen() / 255f, colour.getBlue() / 255f);

        GL11.glVertex3f(start.x, start.y, start.z);
        GL11.glVertex3f(end.x, end.y, end.z);
    }
}
