package me.definedoddy.engine.rendering.object;

import me.definedoddy.toolkit.memory.Disposable;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL15.*;

public class Vbo implements Disposable {
    private final int id;
    private long sizeInBytes;

    public Vbo() {
        id = glGenBuffers();
    }

    public static ByteBuffer create(int index, int size, float[] data) {
        ByteBuffer buffer = ByteBuffer.allocateDirect(data.length * 4);
        for (float f : data) {
            buffer.putFloat(f);
        }
        buffer.flip();
        return buffer;
    }

    public void storeData(ByteBuffer buffer) {
        bind();
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        unbind();
        sizeInBytes = buffer.capacity();
    }

    public long getSizeInBytes() {
        return sizeInBytes;
    }

    public void bind() {
        glBindBuffer(GL_ARRAY_BUFFER, id);
    }

    public void unbind() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    @Override
    public void dispose() {
        glDeleteBuffers(id);
    }

    public int getId() {
        return id;
    }
}
