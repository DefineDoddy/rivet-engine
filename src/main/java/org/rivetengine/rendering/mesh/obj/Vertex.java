package org.rivetengine.rendering.mesh.obj;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class Vertex {
    private final int index;
    private final Vector3f position;
    private final Vector2f textureCoords;
    private final Vector3f normal;

    public Vertex(int index, Vector3f position, Vector2f textureCoords, Vector3f normal) {
        this.index = index;
        this.position = position;
        this.textureCoords = textureCoords;
        this.normal = normal;
    }

    public int getIndex() {
        return index;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector2f getTextureCoord() {
        return textureCoords;
    }

    public Vector3f getNormal() {
        return normal;
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "index=" + index +
                ", position=" + position +
                ", textureCoords=" + textureCoords +
                ", normal=" + normal +
                "}";
    }
}
