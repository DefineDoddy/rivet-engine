package org.rivetengine.rendering.mesh;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.rivetengine.physics.collision.BoundingBox;

import java.util.ArrayList;
import java.util.List;

public class MeshBuilder {
    private final List<Float> positions = new ArrayList<>();
    private final List<Float> texCoords = new ArrayList<>();
    private final List<Float> normals = new ArrayList<>();
    private final List<Integer> indices = new ArrayList<>();

    private final Vector3f min = new Vector3f(Float.MAX_VALUE);
    private final Vector3f max = new Vector3f(-Float.MAX_VALUE);

    public int addVertex(Vector3f position, Vector2f texCoord, Vector3f normal) {
        int index = positions.size() / 3;

        positions.add(position.x);
        positions.add(position.y);
        positions.add(position.z);

        min.min(position);
        max.max(position);

        texCoords.add(texCoord.x);
        texCoords.add(texCoord.y);

        normals.add(normal.x);
        normals.add(normal.y);
        normals.add(normal.z);

        return index;
    }

    public void addIndex(int index) {
        indices.add(index);
    }

    public boolean isEmpty() {
        return indices.isEmpty();
    }

    public Mesh build() {
        return new Mesh(
                toFloatArray(positions),
                toFloatArray(texCoords),
                toFloatArray(normals),
                toIntArray(indices),
                BoundingBox.fromMinMax(min, max));
    }

    private float[] toFloatArray(List<Float> list) {
        float[] arr = new float[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }

    private int[] toIntArray(List<Integer> list) {
        int[] arr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }
}
