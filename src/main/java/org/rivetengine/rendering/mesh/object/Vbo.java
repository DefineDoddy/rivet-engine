package org.rivetengine.rendering.mesh.object;

import org.rivetengine.toolkit.memory.Disposable;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Objects;

import static org.lwjgl.opengl.GL15.*;

public class Vbo implements Disposable {
    private final int id;

    private int componentSize;
    private int length;
    private final boolean isIndices;

    private FloatBuffer data;
    private IntBuffer indices;

    public Vbo(boolean isIndices) {
        id = glGenBuffers();
        this.isIndices = isIndices;
    }

    public void storeData(FloatBuffer data, int componentSize) {
        this.data = data;
        this.componentSize = componentSize;
        this.length = data.limit() / componentSize;
        glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
    }

    public void storeIndices(IntBuffer indices) {
        this.indices = indices;
        this.length = indices.limit();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
    }

    public void bind() {
        if (isIndices)
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id);
        else
            glBindBuffer(GL_ARRAY_BUFFER, id);
    }

    public void unbind() {
        if (isIndices)
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        else
            glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    @Override
    public void dispose() {
        glDeleteBuffers(id);
    }

    public int getId() {
        return id;
    }

    public int getComponentSize() {
        return componentSize;
    }

    public int getLength() {
        return length;
    }

    public boolean isIndices() {
        return isIndices;
    }

    public FloatBuffer getData() {
        return data;
    }

    public IntBuffer getIndices() {
        return indices;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;

        Vbo vbo = (Vbo) obj;
        return vbo.componentSize == componentSize &&
                vbo.length == length &&
                vbo.isIndices == isIndices &&
                Objects.equals(data, vbo.data) &&
                Objects.equals(indices, vbo.indices);
    }
}
