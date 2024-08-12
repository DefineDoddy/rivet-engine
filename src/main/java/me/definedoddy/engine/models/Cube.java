package me.definedoddy.engine.models;

import me.definedoddy.engine.rendering.object.model.Model;
import me.definedoddy.engine.rendering.texture.Material;
import org.joml.Vector3f;

public class Cube extends Model {
    private final Vector3f size;

    public Cube(Vector3f size, Material material) {
        super(null, material);
        this.size = size;
    }

    /*private static OldMesh createMesh(Vector3f size) {
        OldMesh mesh = new OldMesh();
        mesh.setVertexPositions(genVertexPositions(size));
        mesh.setNormals(NORMALS);
        mesh.setTextureCoords(TEXTURE_COORDS);
        mesh.setIndices(INDICES);
        return mesh;
    }*/

    public Vector3f getSize() {
        return size;
    }

    private static float[] genVertexPositions(Vector3f size) {
        float hw = size.x / 2.0f; // half-width
        float hh = size.y / 2.0f; // half-height
        float hd = size.z / 2.0f; // half-depth

        return new float[] {
                -hw, -hh, -hd,
                -hw,  hh, -hd,
                hw, hh, -hd,
                hw, -hh, -hd,

                hw, -hh, hd,
                hw,  hh, hd,
                -hw, hh, hd,
                -hw, -hh, hd,
        };
    }

    private static float[] calculateNormals(float[] positions, int[] indices) {
        int numVertices = positions.length / 3;
        Vector3f[] normals = new Vector3f[numVertices];

        // Initialize normals to zero vectors
        for (int i = 0; i < numVertices; i++) {
            normals[i] = new Vector3f();
        }

        // Calculate face normals and accumulate them into vertex normals
        for (int i = 0; i < indices.length; i += 3) {
            int i0 = indices[i];
            int i1 = indices[i + 1];
            int i2 = indices[i + 2];

            Vector3f v1 = new Vector3f(positions[i1 * 3] - positions[i0 * 3],
                    positions[i1 * 3 + 1] - positions[i0 * 3 + 1],
                    positions[i1 * 3 + 2] - positions[i0 * 3 + 2]);
            Vector3f v2 = new Vector3f(positions[i2 * 3] - positions[i0 * 3],
                    positions[i2 * 3 + 1] - positions[i0 * 3 + 1],
                    positions[i2 * 3 + 2] - positions[i0 * 3 + 2]);

            Vector3f normal = new Vector3f(v1).cross(v2).normalize();

            normals[i0].add(normal);
            normals[i1].add(normal);
            normals[i2].add(normal);
        }

        // Normalize vertex normals
        for (Vector3f normal : normals) {
            normal.normalize();
        }

        // Convert Vector3f[] to float[] for compatibility
        float[] normalArray = new float[numVertices * 3];
        for (int i = 0; i < numVertices; i++) {
            normalArray[i * 3] = normals[i].x;
            normalArray[i * 3 + 1] = normals[i].y;
            normalArray[i * 3 + 2] = normals[i].z;
        }

        return normalArray;
    }

    private static final float[] NORMALS = {
            -0.40824828f, -0.40824828f, -0.81649655f,
            -0.6666667f, 0.6666667f, -0.33333334f,
            0.40824828f, 0.40824828f, -0.81649655f,
            0.6666667f, -0.6666667f, -0.33333334f,
            0.40824828f, -0.40824828f, 0.81649655f,
            0.6666667f, 0.6666667f, 0.33333334f,
            -0.40824828f, 0.40824828f, 0.81649655f,
            -0.6666667f, -0.6666667f, 0.33333334f
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

    private static final float[] TEXTURE_COORDS = {
            0, 0,
            0, 1,
            1, 1,
            1, 0,

            0, 0,
            0, 1,
            1, 1,
            1, 0
    };
}
