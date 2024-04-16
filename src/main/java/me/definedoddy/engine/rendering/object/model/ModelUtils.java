package me.definedoddy.engine.rendering.object.model;

import me.definedoddy.engine.rendering.texture.Texture;

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
}
