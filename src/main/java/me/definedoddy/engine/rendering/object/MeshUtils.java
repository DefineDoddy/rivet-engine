package me.definedoddy.engine.rendering.object;

import me.definedoddy.engine.rendering.texture.Texture;

public class MeshUtils {
    public static Mesh createMesh(float[] vertices, float[] texCoords,
                                  float[] normals, int[] indices, Texture texture) {
        Mesh mesh = new Mesh();
        mesh.setVertexPositions(vertices);
        mesh.setTextureCoords(texCoords);
        mesh.setNormals(normals);
        mesh.setIndices(indices);
        mesh.setTexture(texture);
        return mesh;
    }
}
