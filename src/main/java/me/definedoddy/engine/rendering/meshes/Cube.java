package me.definedoddy.engine.rendering.meshes;

import me.definedoddy.engine.rendering.mesh.Mesh;
import me.definedoddy.engine.rendering.mesh.obj.Vertex;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class Cube extends Mesh {
    public Cube(Vector3f size) {
        super("cube", 0);

        for (Vertex vertex : createVertices(size)) {
            this.addVertex(vertex);
        }

        for (int index : INDICES) {
            this.addIndex(index);
        }
    }

    private static Vertex[] createVertices(Vector3f size) {
        float hw = size.x / 2f;
        float hh = size.y / 2f;
        float hd = size.z / 2f;

        Vector3f[] positions = {
                new Vector3f(hw, hh, -hd),
                new Vector3f(hw, -hh, -hd),
                new Vector3f(hw, hh, hd),
                new Vector3f(hw, -hh, hd),
                new Vector3f(-hw, hh, -hd),
                new Vector3f(-hw, -hh, -hd),
                new Vector3f(-hw, hh, hd),
                new Vector3f(-hw, -hh, hd),
        };

        Vector3f[] normals = {
                new Vector3f(0f, 1f, 0f),
                new Vector3f(0f, 0f, 1f),
                new Vector3f(-1f, 0f, 0f),
                new Vector3f(0f, -1f, 0f),
                new Vector3f(1f, 0f, 0f),
                new Vector3f(0f, 0f, -1f)
        };

        Vector2f[] texCoords = {
                new Vector2f(0, 0),
                new Vector2f(0, 1),
                new Vector2f(1, 1),
                new Vector2f(1, 0),
                new Vector2f(0, 0),
                new Vector2f(0, 1),
                new Vector2f(1, 1),
                new Vector2f(1, 0)
        };

        Vertex[] vertices = new Vertex[8];
        for (int i = 0; i < 8; i++) {
            vertices[i] = new Vertex(i, positions[i], texCoords[i], normals[i / 4]);
        }
        return vertices;
    }

    private static final int[] INDICES = {
            1, 5, 7, 3,
            4, 3, 7, 8,
            8, 7, 5, 6,
            6, 2, 4, 8,
            2, 1, 3, 4,
            6, 5, 1, 2
    };
}
