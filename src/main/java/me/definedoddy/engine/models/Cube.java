package me.definedoddy.engine.models;

import me.definedoddy.engine.rendering.model.mesh.Mesh;
import me.definedoddy.engine.rendering.model.mesh.MeshBuilder;
import me.definedoddy.engine.rendering.model.model.Model;
import me.definedoddy.engine.rendering.texture.Material;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Cube extends Model {
    private final Vector3f size;

    public Cube(Vector3f size, Material material) {
        super(createMesh(size), material);
        this.size = size;
    }

    private static Mesh createMesh(Vector3f size) {
        MeshBuilder builder = new MeshBuilder();
        builder.setVertexPositions(genVertexPositions(size));
        builder.setNormals(NORMALS);
        builder.setTextureCoords(TEXTURE_COORDS);
        builder.setIndices(INDICES);
        return builder.build();
    }

    public Vector3f getSize() {
        return size;
    }

    private static Vector3f[] genVertexPositions(Vector3f size) {
        float hw = size.x / 2.0f; // half-width
        float hh = size.y / 2.0f; // half-height
        float hd = size.z / 2.0f; // half-depth

        return new Vector3f[] {
                new Vector3f(hw, hh, -hd),
                new Vector3f(hw, -hh, -hd),
                new Vector3f(hw, hh, hd),
                new Vector3f(hw, -hh, hd),
                new Vector3f(-hw, hh, -hd),
                new Vector3f(-hw,  -hh, -hd),
                new Vector3f(-hw, hh, hd),
                new Vector3f(-hw, -hh, hd),
        };
    }

    private static final Vector3f[] NORMALS = {
            new Vector3f(0f, 1f, 0f),
            new Vector3f(0f, 0f, 1f),
            new Vector3f(-1f, 0f, 0f),
            new Vector3f(0f, -1f, 0f),
            new Vector3f(1f, 08f, 0f),
            new Vector3f(0f, 0f, -1f)
    };

    private static final int[] INDICES = {
            1, 5, 7, 3,
            4, 3, 7, 8,
            8, 7, 5, 6,
            6, 2, 4, 8,
            2, 1, 3, 4,
            6, 5, 1, 2
    };

    private static final Vector2f[] TEXTURE_COORDS = {
            new Vector2f(0, 0),
            new Vector2f(0, 1),
            new Vector2f(1, 1),
            new Vector2f(1, 0),

            new Vector2f(0, 0),
            new Vector2f(0, 1),
            new Vector2f(1, 1),
            new Vector2f(1, 0)
    };
}
