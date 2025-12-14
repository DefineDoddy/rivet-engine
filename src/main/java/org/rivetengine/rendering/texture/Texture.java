package org.rivetengine.rendering.texture;

import org.rivetengine.rendering.Rendering;
import org.rivetengine.toolkit.memory.Disposable;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.ByteBuffer;

public class Texture implements Disposable {
    public final int id;
    public final int width;
    public final int height;
    public final ByteBuffer data;
    public final TextureType type;

    public Texture(int id, TextureType type, ByteBuffer data, int width, int height) {
        this.id = id;
        this.type = type;
        this.data = data;
        this.width = width;
        this.height = height;

        generateMipmaps();
    }

    private void generateMipmaps() {
        bind();

        if (Rendering.USE_MIPMAPPING) {
            GL30.glGenerateMipmap(type.getGlType());
            GL11.glTexParameteri(type.getGlType(), GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
            GL11.glTexParameterf(type.getGlType(), GL14.GL_TEXTURE_LOD_BIAS, Rendering.LOD_BIAS);
        }

        unbind();
    }

    public void bind() {
        bind(0);
    }

    public void bind(int unit) {
        bind(id, type, unit);
    }

    public void unbind() {
        unbind(0);
    }

    public void unbind(int unit) {
        unbind(type, unit);
    }

    public static void bind(int id, TextureType type, int unit) {
        GL20.glActiveTexture(GL20.GL_TEXTURE0 + unit);
        GL20.glBindTexture(type.getGlType(), id);
    }

    public static void unbind(TextureType type, int unit) {
        GL20.glBindTexture(type.getGlType(), unit);
    }

    @Override
    public void dispose() {
        GL20.glDeleteTextures(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Texture texture)) {
            return false;
        }
        return texture.id == id;
    }

    @Override
    public String toString() {
        return "Texture{" +
                "id=" + id +
                ", width=" + width +
                ", height=" + height +
                ", type=" + type +
                "}";
    }
}
