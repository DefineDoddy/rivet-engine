package me.definedoddy.toolkit.model.obj;

import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class VertexData {
    public final List<Vector3f> positions = new ArrayList<>();
    public final List<Vector2f> textureCoords = new ArrayList<>();
    public final List<Vector3f> normals = new ArrayList<>();

    public void clear() {
        positions.clear();
        textureCoords.clear();
        normals.clear();
    }
}
