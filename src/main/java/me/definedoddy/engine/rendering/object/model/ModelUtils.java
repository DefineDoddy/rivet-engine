package me.definedoddy.engine.rendering.object.model;

import me.definedoddy.engine.rendering.object.mesh.Mesh;
import me.definedoddy.engine.rendering.texture.Material;
import me.definedoddy.engine.rendering.texture.MaterialBuilder;
import me.definedoddy.engine.rendering.texture.Texture;
import org.joml.Vector3f;

public class ModelUtils {
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
        float[] vertices = new float[] {
                -size.x / 2, size.y / 2, 0,
                -size.x / 2, -size.y / 2, 0,
                size.x / 2, -size.y / 2, 0,
                size.x / 2, size.y / 2, 0
        };
        float[] texCoords = new float[] {
                0, 0,
                0, 1,
                1, 1,
                1, 0
        };
        float[] normals = new float[] {
                0, 0, 1,
                0, 0, 1,
                0, 0, 1,
                0, 0, 1
        };
        int[] indices = new int[] {
                0, 1, 3,
                3, 1, 2
        };
        return createModel(vertices, texCoords, normals, indices, texture);
    }
}
