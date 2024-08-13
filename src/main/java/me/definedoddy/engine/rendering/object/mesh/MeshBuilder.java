package me.definedoddy.engine.rendering.object.mesh;

import me.definedoddy.toolkit.model.obj.Vertex;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class MeshBuilder {
    private final Mesh mesh = new Mesh("mesh", 0);

    private Vector3f[] vertexPositions;
    private Vector2f[] textureCoords;
    private Vector3f[] normals;
    private int[] indices;

    private boolean renderQuads;

    public void setVertexPositions(Vector3f[] vertexPositions) {
        this.vertexPositions = vertexPositions;
    }

    public void setTextureCoords(Vector2f[] textureCoords) {
        this.textureCoords = textureCoords;
    }

    public void setNormals(Vector3f[] normals) {
        this.normals = normals;
    }

    public void setIndices(int[] indices) {
        this.indices = indices;
    }

    public void setRenderQuads(boolean renderQuads) {
        this.renderQuads = renderQuads;
    }

    public Mesh build() {
        for (int i = 0; i < vertexPositions.length; i++) {
            mesh.addVertex(new Vertex(i, vertexPositions[i], textureCoords[i], normals[i]));
        }

        for (int index : indices) {
            mesh.addIndex(index);
        }

        mesh.process();

        return mesh;
    }
}
