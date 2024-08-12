package me.definedoddy.engine.rendering.object.model;

import me.definedoddy.engine.rendering.object.mesh.OldMesh;
import me.definedoddy.engine.rendering.texture.Material;

public class ModelUtils {
    public static Model createModel(float[] vertices, float[] texCoords,
                                   float[] normals, int[] indices, Material material) {
        OldMesh mesh = new OldMesh();
        mesh.setVertexPositions(vertices);
        mesh.setTextureCoords(texCoords);
        mesh.setNormals(normals);
        mesh.setIndices(indices);

        return null;
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
}
