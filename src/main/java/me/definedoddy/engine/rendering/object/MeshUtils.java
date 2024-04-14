package me.definedoddy.engine.rendering.object;

public class MeshUtils {
    public static Mesh createMesh(float[] vertices, float[] texCoords, float[] normals, int[] indices) {
        Mesh mesh = new Mesh();
        mesh.setVertexPositions(vertices);
        mesh.setTextureCoords(texCoords);
        mesh.setNormals(normals);
        mesh.setIndices(indices);
        return mesh;
    }
}
