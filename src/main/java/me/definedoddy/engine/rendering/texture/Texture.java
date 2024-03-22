package me.definedoddy.engine.rendering.texture;

import me.definedoddy.toolkit.memory.Disposable;
import org.lwjgl.opengl.GL20;

public class Texture implements Disposable {
    private final int id;
    private final int width;
    private final int height;

    public Texture(int id, int width, int height) {
        this.id = id;
        this.width = width;
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public void dispose() {
        GL20.glDeleteTextures(id);
    }
}
