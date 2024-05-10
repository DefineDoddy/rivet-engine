package me.definedoddy.engine.rendering.object.mesh;

import me.definedoddy.engine.rendering.object.Vao;
import me.definedoddy.toolkit.buffer.BufferUtils;
import me.definedoddy.toolkit.memory.Disposable;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Mesh implements Disposable {
    private final Vao vao = new Vao();

    public void render() {
        GL11.glDrawElements(GL11.GL_TRIANGLES, vao.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
    }

    public void setVertexPositions(float[] vertPositions) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertPositions);

        vao.bind();
        vao.storeData(0, buffer, 3);
        vao.unbind();
    }

    public void setTextureCoords(float[] texCoords) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(texCoords);

        vao.bind();
        vao.storeData(1, buffer, 2);
        vao.unbind();
    }

    public void setNormals(float[] normals) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(normals);

        vao.bind();
        vao.storeData(2, buffer, 3);
        vao.unbind();
    }

    public void setIndices(int[] indices) {
        IntBuffer buffer = BufferUtils.createIntBuffer(indices);

        vao.bind();
        vao.storeIndices(buffer);
        vao.unbind();
    }

    public Vao getVao() {
        return vao;
    }

    public void bind() {
        vao.bind();
    }

    public void unbind() {
        vao.unbind();
    }

    @Override
    public void dispose() {
        vao.dispose();
    }
}
