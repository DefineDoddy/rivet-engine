package org.rivetengine.rendering.mesh.object;

import org.rivetengine.toolkit.memory.Disposable;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL30.*;

public class Vao implements Disposable {
    private final int id;
    private final List<Vbo> vbos = new ArrayList<>();

    private int indicesCount = -1;
    private int maxIndex;

    public Vao() {
        id = glGenVertexArrays();
    }

    public void storeData(int index, FloatBuffer data, int componentSize) {
        this.maxIndex = Math.max(this.maxIndex, index);
        bind();

        Vbo vbo = new Vbo(false);
        vbo.bind();

        vbo.storeData(data, componentSize);
        glVertexAttribPointer(index, componentSize, GL_FLOAT, false, 0, 0);

        vbo.unbind();
        vbos.add(vbo);

        unbind();
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

        for (Vbo vbo : vbos)
            if (vbo.isIndices())
                vbo.bind();
        for (int i = 0; i <= maxIndex; i++)
            glEnableVertexAttribArray(i);
    }

    public void unbind() {
        glBindVertexArray(0);

        for (Vbo vbo : vbos)
            if (vbo.isIndices())
                vbo.unbind();
        for (int i = 0; i <= maxIndex; i++)
            glDisableVertexAttribArray(i);
    }

    @Override
    public void dispose() {
        glDeleteVertexArrays(id);
        for (Vbo vbo : vbos)
            vbo.dispose();
    }

    public int getId() {
        return id;
    }

    public int getVertexCount() {
        if (indicesCount != -1)
            return indicesCount;

        Vbo vbo = vbos.getFirst();
        return vbo.getData().limit() / vbo.getComponentSize();
    }

    public boolean hasIndices() {
        return indicesCount != -1;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Vao vao))
            return false;
        return vao.id == id;
    }
}
