package me.definedoddy.engine.models;

import me.definedoddy.engine.rendering.object.mesh.Mesh;
import me.definedoddy.engine.rendering.object.model.Model;
import me.definedoddy.engine.rendering.texture.Material;
import org.joml.Vector3f;

public class Cube extends Model {
    private final Vector3f size;

    public Cube(Vector3f size, Material material) {
        super(createMesh(size), material);
        this.size = size;
    }

    private static Mesh createMesh(Vector3f size) {
        Mesh mesh = new Mesh();
        mesh.setVertexPositions(genVertexPositions(size));
        mesh.setNormals(NORMALS);
        mesh.setTextureCoords(TEXTURE_COORDS);
        mesh.setRenderQuads(true);
        return mesh;
    }

    public Vector3f getSize() {
        return size;
    }

    private static float[] genVertexPositions(Vector3f size) {
        float x = size.x / 2;
        float y = size.y / 2;
        float z = size.z / 2;

        // 6 sides, 4 vertices each

        return new float[] {
                // render quads
                -x, -y, z,
                x, -y, z,
                x, y, z,
                -x, y, z,

                x, -y, z,
                x, -y, -z,
                x, y, -z,
                x, y, z,

                x, -y, -z,
                -x, -y, -z,
                -x, y, -z,
                x, y, -z,

                -x, -y, -z,
                -x, -y, z,
                -x, y, z,
                -x, y, -z,

                -x, -y, -z,
                x, -y, -z,
                x, -y, z,
                -x, -y, z,

                -x, y, z,
                x, y, z,
                x, y, -z,
                -x, y, -z
        };
    }

    private static final float[] NORMALS = new float[] {
            0, 0, 1,
            1, 0, 0,
            0, 0, -1,
            -1, 0, 0,
            0, 1, 0,
            0, -1, 0
    };

    private static final float[] TEXTURE_COORDS = new float[] {
            0, 0,
            1, 0,
            1, 1,
            0, 1,
            0, 0,
            1, 0,
            1, 1,
            0, 1
    };

    private static final int[] INDICES = new int[] {
            0, 1, 2, 2, 3, 0, // Front
            1, 5, 6, 6, 2, 1, // Right
            7, 6, 5, 5, 4, 7, // Back
            4, 0, 3, 3, 7, 4, // Left
            4, 5, 1, 1, 0, 4, // Bottom
            3, 2, 6, 6, 7, 3  // Top
    };
}
