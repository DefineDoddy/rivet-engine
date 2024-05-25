package me.definedoddy.engine.rendering.texture;

import org.lwjgl.opengl.GL20;

public enum TextureType {
    DIFFUSE(GL20.GL_TEXTURE_2D),
    NORMAL(GL20.GL_TEXTURE_2D),
    SPECULAR(GL20.GL_TEXTURE_2D),
    CUBEMAP(GL20.GL_TEXTURE_CUBE_MAP);

    private final int glType;

    TextureType(int glType) {
        this.glType = glType;
    }

    public int getGlType() {
        return glType;
    }
}
