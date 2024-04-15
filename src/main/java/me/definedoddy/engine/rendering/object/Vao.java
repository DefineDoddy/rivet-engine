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

    public Vao() {
        id = glGenVertexArrays();
    }

    public void storeData(int index, FloatBuffer data, int componentSize) {
        Vbo vbo = new Vbo();
        vbo.bind();
        vbo.storeData(data, componentSize);
        glVertexAttribPointer(index, componentSize, GL_FLOAT, false, 0, 0);
        vbo.unbind();
        vbos.add(vbo);
    }

    public void storeIndices(IntBuffer indices) {
        Vbo vbo = new Vbo();
        vbo.bind();
        vbo.storeIndices(indices);
        vbo.unbind();
        vbos.add(vbo);
    }

    public void bind() {
        glBindVertexArray(id);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        glActiveTexture(GL_TEXTURE0);
    }

    public void unbind() {
        glBindVertexArray(0);
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
        return vbos.getFirst().getLength();
    }
}
