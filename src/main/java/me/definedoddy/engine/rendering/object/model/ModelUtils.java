package me.definedoddy.engine.rendering.object.model;

import me.definedoddy.engine.rendering.texture.Texture;
import org.joml.Vector3f;

public class ModelUtils {
    public static Model createMesh(float[] vertices, float[] texCoords,
                                   float[] normals, int[] indices, Texture texture) {
        Model model = new Model();
        model.setVertexPositions(vertices);
        model.setTextureCoords(texCoords);
        model.setNormals(normals);
        model.setIndices(indices);
        model.setTexture(texture);
        return model;
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
        return createMesh(vertices, texCoords, normals, indices, texture);
    }
}
