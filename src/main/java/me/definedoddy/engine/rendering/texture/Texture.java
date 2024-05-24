package me.definedoddy.engine.rendering.texture;

import me.definedoddy.engine.manager.GameManager;
import me.definedoddy.engine.rendering.config.RenderConfig;
import me.definedoddy.toolkit.memory.Disposable;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

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

        generateMipmaps();
    }

    private void generateMipmaps() {
        RenderConfig renderConfig = GameManager.getRenderEngine().getRenderConfig();
        bind();

        if (renderConfig.useMipmapping()) {
            GL30.glGenerateMipmap(type.getGlType());
            GL11.glTexParameteri(type.getGlType(), GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
            GL11.glTexParameterf(type.getGlType(), GL14.GL_TEXTURE_LOD_BIAS, renderConfig.getLODBias());
        }

        unbind();
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
        bind(0);
    }

    public void bind(int unit) {
        bind(id, type, unit);
    }

    public void unbind() {
        unbind(type);
    }

    public static void bind(int id, TextureType type, int unit) {
        GL20.glActiveTexture(GL20.GL_TEXTURE0 + unit);
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
