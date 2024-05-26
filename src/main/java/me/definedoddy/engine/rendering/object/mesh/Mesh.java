package me.definedoddy.engine.rendering.object.mesh;

import me.definedoddy.engine.rendering.object.Vao;
import me.definedoddy.toolkit.buffer.BufferUtils;
import me.definedoddy.toolkit.memory.Disposable;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Mesh implements Disposable, Comparable<Mesh> {
    private final Vao vao = new Vao();
    private boolean renderQuads;

    public void render() {
        int renderMode = renderQuads ? GL11.GL_QUADS : GL11.GL_TRIANGLES;

        if (vao.hasIndices()) {
            GL11.glDrawElements(renderMode, vao.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        } else {
            GL11.glDrawArrays(renderMode, 0, vao.getVertexCount());
        }
    }

    public void setRenderQuads(boolean renderQuads) {
        this.renderQuads = renderQuads;
    }

    public boolean isRenderQuads() {
        return renderQuads;
    }

    public void setVertexPositions(float[] vertPositions) {
        if (vertPositions == null) return;
        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertPositions);
        vao.storeData(0, buffer, 3);
    }

    public void setTextureCoords(float[] texCoords) {
        if (texCoords == null) return;
        FloatBuffer buffer = BufferUtils.createFloatBuffer(texCoords);
        vao.storeData(1, buffer, 2);
    }

    public void setNormals(float[] normals) {
        if (normals == null) return;
        FloatBuffer buffer = BufferUtils.createFloatBuffer(normals);
        vao.storeData(2, buffer, 3);
    }

    public void setIndices(int[] indices) {
        if (indices == null) return;
        IntBuffer buffer = BufferUtils.createIntBuffer(indices);
        vao.storeIndices(buffer);
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

    public int getVertexCount() {
        return vao.getVertexCount();
    }

    @Override
    public void dispose() {
        vao.dispose();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        return vao.equals(((Mesh) obj).vao);
    }

    @Override
    public int compareTo(Mesh o) {
        if (vao.equals(o.vao)) return 0;
        if (vao.getId() < o.vao.getId()) return -1;
        return 1;
    }
}
