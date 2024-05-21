package me.definedoddy.engine.rendering.object.model;

import me.definedoddy.engine.rendering.object.mesh.Mesh;
import me.definedoddy.engine.rendering.texture.Material;
import me.definedoddy.engine.rendering.texture.MaterialBuilder;
import me.definedoddy.engine.rendering.texture.Texture;
import org.joml.Vector3f;

public class ModelUtils {
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

    public static Model createModel(float[] vertices, float[] texCoords,
                                   float[] normals, int[] indices, Texture texture) {
        Mesh mesh = new Mesh();
        mesh.setVertexPositions(vertices);
        mesh.setTextureCoords(texCoords);
        mesh.setNormals(normals);
        mesh.setIndices(indices);

        Material material = MaterialBuilder.fromDiffuse(texture);
        return new Model(mesh, material);
    }

    public static Model createQuad(Vector3f size, Texture texture) {
        return createModel(generateQuadVertices(size), QUAD_TEX_COORDS, QUAD_NORMALS, QUAD_INDICES, texture);
    }
}
