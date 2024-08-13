package me.definedoddy.engine.models;

import me.definedoddy.engine.rendering.object.mesh.Mesh;
import me.definedoddy.engine.rendering.object.mesh.MeshBuilder;
import me.definedoddy.engine.rendering.object.model.Model;
import me.definedoddy.engine.rendering.texture.Material;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Quad extends Model {
    private final Vector3f size;

    public Quad(Vector3f size, Material material) {
        super(createMesh(size), material);
        this.size = size;
    }

    private static Mesh createMesh(Vector3f size) {
        MeshBuilder builder = new MeshBuilder();
        builder.setVertexPositions(genVertexPositions(size));
        builder.setNormals(NORMALS);
        builder.setTextureCoords(TEXTURE_COORDS);
        builder.setIndices(INDICES);
        builder.setRenderQuads(true);
        return builder.build();
    }

    public Vector3f getSize() {
        return size;
    }

    private static Vector3f[] genVertexPositions(Vector3f size) {
        return new Vector3f[] {
                new Vector3f(-size.x / 2, size.y / 2, 0),
                new Vector3f(-size.x / 2, -size.y / 2, 0),
                new Vector3f(size.x / 2, -size.y / 2, 0),
                new Vector3f(size.x / 2, size.y / 2, 0)
        };
    }

    private static final Vector3f[] NORMALS = new Vector3f[] {
            new Vector3f(0, 0, 1),
            new Vector3f(0, 0, 1),
            new Vector3f(0, 0, 1),
            new Vector3f(0, 0, 1)
    };

    private static final Vector2f[] TEXTURE_COORDS = new Vector2f[] {
            new Vector2f(0, 0),
            new Vector2f(0, 1),
            new Vector2f(1, 1),
            new Vector2f(1, 0)
    };

    private static final int[] INDICES = new int[] {
            0, 1, 3,
            3, 1, 2
    };
}
