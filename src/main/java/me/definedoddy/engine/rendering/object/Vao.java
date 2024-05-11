package me.definedoddy.engine.rendering.object;

import me.definedoddy.toolkit.memory.Disposable;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL30.*;

public class Vao implements Disposable {
    private final int id;
    private final List<Vbo> vbos = new ArrayList<>();

    private int indicesCount;

    public Vao() {
        id = glGenVertexArrays();
    }

    public void storeData(int index, FloatBuffer data, int componentSize) {
        Vbo vbo = new Vbo(false);
        vbo.bind();
        vbo.storeData(data, componentSize);
        glVertexAttribPointer(index, componentSize, GL_FLOAT, false, 0, 0);
        vbo.unbind();
        vbos.add(vbo);
    }

    public void storeIndices(IntBuffer indices) {
        indicesCount = indices.limit();
        Vbo vbo = new Vbo(true);
        vbo.bind();
        vbo.storeIndices(indices);
        vbo.unbind();
        vbos.add(vbo);
    }

    public void bind() {
        glBindVertexArray(id);
        for (Vbo vbo : vbos) if (vbo.isIndices()) vbo.bind();
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        glActiveTexture(GL_TEXTURE0);
    }

    public void unbind() {
        glBindVertexArray(0);
        for (Vbo vbo : vbos) if (vbo.isIndices()) vbo.unbind();
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
    }

    @Override
    public void dispose() {
        glDeleteVertexArrays(id);
        for (Vbo vbo : vbos) vbo.dispose();
    }

    public int getId() {
        return id;
    }

    public int getVertexCount() {
        return indicesCount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        for (int i = 0; i < vbos.size(); i++) {
            if (!vbos.get(i).equals(((Vao) obj).vbos.get(i))) return false;
        }

        return true;
    }
}
