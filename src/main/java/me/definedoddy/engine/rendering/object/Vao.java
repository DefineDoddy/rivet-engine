package me.definedoddy.engine.rendering.object;

import me.definedoddy.toolkit.memory.Disposable;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL30.*;

public class Vao implements Disposable {
    private final int id;

    private final List<Vbo> vbos = new ArrayList<>();
    private final List<Attribute> attributes = new ArrayList<>();

    public Vao() {
        id = glGenVertexArrays();
    }

    public Vbo storeData(ByteBuffer buffer, Attribute... attributes) {
        Vbo vbo = new Vbo();
        vbo.bind();
        vbo.storeData(buffer);
        vbos.add(vbo);
        linkAttributes(attributes);
        vbo.unbind();
        return vbo;
    }

    public void linkAttributes(Attribute... attributes) {
        int offset = 0;
        for (Attribute attribute : attributes) {
            attribute.link(offset, getVertexDataTotalBytes(attributes));
            attribute.setEnabled(true);
            this.attributes.add(attribute);
            offset += attribute.getBytesPerVertex();
        }
    }

    public void bind() {
        glBindVertexArray(id);
    }

    public void unbind() {
        glBindVertexArray(0);
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
        return (int) (vbos.getFirst().getSizeInBytes() / attributes.getFirst().getBytesPerVertex());
    }

    private int getVertexDataTotalBytes(Attribute... attributes) {
        int total = 0;
        for (Attribute attribute : attributes) {
            total += attribute.getBytesPerVertex();
        }
        return total;
    }
}
