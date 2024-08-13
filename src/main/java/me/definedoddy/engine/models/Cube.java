package me.definedoddy.engine.models;

import me.definedoddy.engine.rendering.object.mesh.Mesh;
import me.definedoddy.engine.rendering.object.mesh.MeshBuilder;
import me.definedoddy.engine.rendering.object.model.Model;
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
                new Vector3f(-hw, -hh, -hd),
                new Vector3f(-hw,  hh, -hd),
                new Vector3f(hw, hh, -hd),
                new Vector3f(hw, -hh, -hd),

                new Vector3f(hw, -hh, hd),
                new Vector3f(hw,  hh, hd),
                new Vector3f(-hw, hh, hd),
                new Vector3f(-hw, -hh, hd),
        };
    }

    private static final Vector3f[] NORMALS = {
            new Vector3f(-0.40824828f, -0.40824828f, -0.81649655f),
            new Vector3f(-0.6666667f, 0.6666667f, -0.33333334f),
            new Vector3f(0.40824828f, 0.40824828f, -0.81649655f),
            new Vector3f(0.6666667f, -0.6666667f, -0.33333334f),
            new Vector3f(0.40824828f, -0.40824828f, 0.81649655f),
            new Vector3f(0.6666667f, 0.6666667f, 0.33333334f),
            new Vector3f(-0.40824828f, 0.40824828f, 0.81649655f),
            new Vector3f(-0.6666667f, -0.6666667f, 0.33333334f )
    };

    private static final int[] INDICES = {
            // Front
            0, 1, 2,
            2, 3, 0,

            // Back
            4, 5, 6,
            6, 7, 4,

            // Top
            1, 6, 5,
            5, 2, 1,

            // Bottom
            7, 0, 3,
            3, 4, 7,

            // Right
            3, 2, 5,
            5, 4, 3,

            // Left
            7, 6, 1,
            1, 0, 7
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
