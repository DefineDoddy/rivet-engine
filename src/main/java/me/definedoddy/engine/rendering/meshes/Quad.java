package me.definedoddy.engine.rendering.meshes;

import me.definedoddy.engine.rendering.mesh.Mesh;
import me.definedoddy.engine.rendering.mesh.obj.Vertex;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class Quad extends Mesh {
    public Quad(Vector2f size) {
        super("quad", 0);

        for (Vertex vertex : createVertices(size)) {
            this.addVertex(vertex);
        }

        for (int index : INDICES) {
            this.addIndex(index);
        }
    }

    private static Vertex[] createVertices(Vector2f size) {
        float hw = size.x / 2f;
        float hh = size.y / 2f;

        Vector3f[] positions = {
                new Vector3f(-hw, 0, hh),
                new Vector3f(hw, 0, hh),
                new Vector3f(-hw, 0, -hh),
                new Vector3f(hw, 0, -hh)
        };

        Vector3f normal = new Vector3f(0f, 1f, 0f);

        Vector2f[] texCoords = {
                new Vector2f(0, 0),
                new Vector2f(1, 0),
                new Vector2f(0, 1),
                new Vector2f(1, 1)
        };

        Vertex[] vertices = new Vertex[4];
        for (int i = 0; i < 4; i++) {
            vertices[i] = new Vertex(i, positions[i], texCoords[i], normal);
        }
        return vertices;
    }

    private static final int[] INDICES = {
            0, 1, 2, 1, 3, 2
    };
}
