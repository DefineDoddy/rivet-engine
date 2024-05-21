package me.definedoddy.engine.rendering.texture;

import me.definedoddy.toolkit.memory.Disposable;
import org.lwjgl.opengl.GL20;

import java.nio.ByteBuffer;

public class Texture implements Disposable {
    private final int id;
    private final int width;
    private final int height;
    private final ByteBuffer data;
    private final TextureType type;

    public Texture(int id, TextureType type, ByteBuffer data, int width, int height) {
        this.id = id;
        this.type = type;
        this.data = data;
        this.width = width;
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public TextureType getType() {
        return type;
    }

    public ByteBuffer getData() {
        return data;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void bind() {
        bind(id, type);
    }

    public static void bind(int id, TextureType type) {
        GL20.glActiveTexture(GL20.GL_TEXTURE0);
        GL20.glBindTexture(type.getGlType(), id);
    }

    public static void unbind(TextureType type) {
        GL20.glBindTexture(type.getGlType(), 0);
    }

    @Override
    public void dispose() {
        GL20.glDeleteTextures(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Texture texture)) return false;
        return texture.id == id;
    }
}
