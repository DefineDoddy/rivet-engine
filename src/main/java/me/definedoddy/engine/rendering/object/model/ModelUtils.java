package me.definedoddy.engine.rendering.object.model;

import me.definedoddy.engine.rendering.object.mesh.Mesh;
import me.definedoddy.engine.rendering.texture.Material;
import org.joml.Vector3f;

public class ModelUtils {
    public static Model createCube(Vector3f size, Material material) {
        // 3d cube with vertices, tex coords, normals, indices
        float[] vertices = new float[] {
                // Right face
                size.x / 2, size.y / 2, size.z / 2,
                size.x / 2, -size.y / 2, size.z / 2,
                size.x / 2, -size.y / 2, -size.z / 2,
                size.x / 2, size.y / 2, -size.z / 2,

                // Left face
                -size.x / 2, size.y / 2, -size.z / 2,
                -size.x / 2, -size.y / 2, -size.z / 2,
                -size.x / 2, -size.y / 2, size.z / 2,
                -size.x / 2, size.y / 2, size.z / 2,

                // Top face
                -size.x / 2, size.y / 2, size.z / 2,
                size.x / 2, size.y / 2, size.z / 2,
                size.x / 2, size.y / 2, -size.z / 2,
                -size.x / 2, size.y / 2, -size.z / 2,

                // Bottom face
                -size.x / 2, -size.y / 2, size.z / 2,
                -size.x / 2, -size.y / 2, -size.z / 2,
                size.x / 2, -size.y / 2, -size.z / 2,
                size.x / 2, -size.y / 2, size.z / 2,

                // Back face
                -size.x / 2, size.y / 2, -size.z / 2,
                -size.x / 2, -size.y / 2, -size.z / 2,
                size.x / 2, -size.y / 2, -size.z / 2,
                size.x / 2, size.y / 2, -size.z / 2,

                // Front face
                -size.x / 2, size.y / 2, size.z / 2,
                -size.x / 2, -size.y / 2, size.z / 2,
                size.x / 2, -size.y / 2, size.z / 2,
                size.x / 2, size.y / 2, size.z / 2
        };

        float[] texCoords = new float[] {
                0, 0,
                0, 1,
                1, 1,
                1, 0,

                0, 0,
                0, 1,
                1, 1,
                1, 0,

                0, 0,
                0, 1,
                1, 1,
                1, 0,

                0, 0,
                0, 1,
                1, 1,
                1, 0,

                0, 0,
                0, 1,
                1, 1,
                1, 0,

                0, 0,
                0, 1,
                1, 1,
                1, 0
        };

        float[] normals = new float[] {
                // Right face
                1, 0, 0,
                1, 0, 0,
                1, 0, 0,
                1, 0, 0,

                // Left face
                -1, 0, 0,
                -1, 0, 0,
                -1, 0, 0,
                -1, 0, 0,

                // Top face
                0, 1, 0,
                0, 1, 0,
                0, 1, 0,
                0, 1, 0,

                // Bottom face
                0, -1, 0,
                0, -1, 0,
                0, -1, 0,
                0, -1, 0,

                // Back face
                0, 0, -1,
                0, 0, -1,
                0, 0, -1,
                0, 0, -1,

                // Front face
                0, 0, 1,
                0, 0, 1,
                0, 0, 1,
                0, 0, 1
        };

        int[] indices = new int[] {
                // Right face
                0, 1, 2,
                2, 3, 0,

                // Left face
                4, 5, 6,
                6, 7, 4,

                // Top face
                8, 9, 10,
                10, 11, 8,

                // Bottom face
                12, 13, 14,
                14, 15, 12,

                // Back face
                16, 17, 18,
                18, 19, 16,

                // Front face
                20, 21, 22,
                22, 23, 20
        };

        return createModel(vertices, texCoords, normals, indices, material);
    }

    public static Model createModel(float[] vertices, float[] texCoords,
                                   float[] normals, int[] indices, Material material) {
        Mesh mesh = new Mesh();
        mesh.setVertexPositions(vertices);
        mesh.setTextureCoords(texCoords);
        mesh.setNormals(normals);
        mesh.setIndices(indices);

        return new Model(mesh, material);
    }

    public static Model createQuad(Vector3f size, Material material) {
        return createModel(generateQuadVertices(size), QUAD_TEX_COORDS, QUAD_NORMALS, QUAD_INDICES, material);
    }

    public static float[] genCubeMapVertices(float size) {
        return new float[] {
                -size,  size, -size,
                -size, -size, -size,
                size, -size, -size,
                size, -size, -size,
                size,  size, -size,
                -size,  size, -size,

                -size, -size,  size,
                -size, -size, -size,
                -size,  size, -size,
                -size,  size, -size,
                -size,  size,  size,
                -size, -size,  size,

                size, -size, -size,
                size, -size,  size,
                size,  size,  size,
                size,  size,  size,
                size,  size, -size,
                size, -size, -size,

                -size, -size,  size,
                -size,  size,  size,
                size,  size,  size,
                size,  size,  size,
                size, -size,  size,
                -size, -size,  size,

                -size,  size, -size,
                size,  size, -size,
                size,  size,  size,
                size,  size,  size,
                -size,  size,  size,
                -size,  size, -size,

                -size, -size, -size,
                -size, -size,  size,
                size, -size, -size,
                size, -size, -size,
                -size, -size,  size,
                size, -size,  size
        };
    }

    public static float[] generateQuadVertices(Vector3f size) {
        return new float[] {
                -size.x / 2, size.y / 2, 0,
                -size.x / 2, -size.y / 2, 0,
                size.x / 2, -size.y / 2, 0,
                size.x / 2, size.y / 2, 0
        };
    }

    public static final float[] QUAD_TEX_COORDS = new float[] {
            0, 0,
            0, 1,
            1, 1,
            1, 0
    };

    public static final float[] QUAD_NORMALS = new float[] {
            0, 0, 1,
            0, 0, 1,
            0, 0, 1,
            0, 0, 1
    };

    public static final int[] QUAD_INDICES = new int[] {
            0, 1, 3,
            3, 1, 2
    };
}
