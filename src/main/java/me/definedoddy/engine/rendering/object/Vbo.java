package me.definedoddy.engine.rendering.object;

import me.definedoddy.toolkit.memory.Disposable;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15.*;

public class Vbo implements Disposable {
    private final int id;

    private int componentSize;
    private int length;
    private boolean isIndices;

    public Vbo() {
        id = glGenBuffers();
    }

    public void storeData(FloatBuffer data, int componentSize) {
        this.isIndices = false;
        this.componentSize = componentSize;
        this.length = data.limit() / componentSize;
        glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
    }

    public void storeIndices(IntBuffer indices) {
        this.isIndices = true;
        this.length = indices.limit();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
    }

    public void bind() {
        if (isIndices) glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id);
        else glBindBuffer(GL_ARRAY_BUFFER, id);
    }

    public void unbind() {
        if (isIndices) glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        else glBindBuffer(GL_ARRAY_BUFFER, 0);
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
}
