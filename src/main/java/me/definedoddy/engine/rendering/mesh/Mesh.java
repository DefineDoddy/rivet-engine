package me.definedoddy.engine.rendering.mesh;

import me.definedoddy.engine.rendering.mesh.obj.Vertex;
import me.definedoddy.engine.rendering.object.Vao;
import me.definedoddy.toolkit.buffer.BufferUtils;
import me.definedoddy.toolkit.memory.Disposable;

import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class Mesh implements Disposable {
    private final String id;
    private final int indicesOffset;

    private final Vao vao = new Vao();

    private final List<Vertex> vertices = new ArrayList<>();
    private final List<Integer> indices = new ArrayList<>();

    public Mesh(String id, int indicesOffset) {
        this.id = id;
        this.indicesOffset = indicesOffset;
    }

    public void render() {
        if (vao.hasIndices()) {
            GL11.glDrawElements(GL11.GL_TRIANGLES, vao.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        } else {
            GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vao.getVertexCount());
        }
    }

    public void bind() {
        vao.bind();
    }

    public void unbind() {
        vao.unbind();
    }

    public void process() {
        FloatBuffer positionBuffer = BufferUtils.createFloatBuffer(getVertexPositions());
        vao.storeData(0, positionBuffer, 3);

        FloatBuffer texCoordBuffer = BufferUtils.createFloatBuffer(getTextureCoords());
        vao.storeData(1, texCoordBuffer, 2);

        FloatBuffer normalBuffer = BufferUtils.createFloatBuffer(getNormals());
        vao.storeData(2, normalBuffer, 3);

        IntBuffer buffer = BufferUtils.createIntBuffer(getIndices());
        vao.storeIndices(buffer);
    }

    public String getId() {
        return id;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public void addVertex(Vertex vertex) {
        vertices.add(vertex);
    }

    public void addIndex(int index) {
        indices.add(index);
    }

    public int getVertexCount() {
        return vertices.size();
    }

    public int getIndicesCount() {
        return indices.size();
    }

    private float[] getVertexPositions() {
        float[] positions = new float[vertices.size() * 3];
        for (int i = 0; i < vertices.size(); i++) {
            Vertex vertex = vertices.get(i);
            positions[i * 3] = vertex.getPosition().x;
            positions[i * 3 + 1] = vertex.getPosition().y;
            positions[i * 3 + 2] = vertex.getPosition().z;
        }
        return positions;
    }

    private float[] getTextureCoords() {
        float[] textureCoords = new float[vertices.size() * 2];
        for (int i = 0; i < vertices.size(); i++) {
            Vertex vertex = vertices.get(i);
            textureCoords[i * 2] = vertex.getTextureCoord().x;
            textureCoords[i * 2 + 1] = vertex.getTextureCoord().y;
        }
        return textureCoords;
    }

    private float[] getNormals() {
        float[] normals = new float[vertices.size() * 3];
        for (int i = 0; i < vertices.size(); i++) {
            Vertex vertex = vertices.get(i);
            normals[i * 3] = vertex.getNormal().x;
            normals[i * 3 + 1] = vertex.getNormal().y;
            normals[i * 3 + 2] = vertex.getNormal().z;
        }
        return normals;
    }

    private int[] getIndices() {
        int[] indices = new int[this.indices.size()];
        for (int i = 0; i < this.indices.size(); i++) {
            indices[i] = this.indices.get(i) + indicesOffset;
        }
        return indices;
    }

    @Override
    public void dispose() {
        vao.dispose();
    }
}
