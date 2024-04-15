package me.definedoddy.engine.rendering.shader;

import org.lwjgl.opengl.GL20;

public class UniformTexture extends Uniform {
    private int textureUnit;

    public UniformTexture(String name) {
        super(name);
    }

    public void loadTexUnit(int unit) {
        textureUnit = unit;
    }


    public void loadTexture() {
        GL20.glUniform1i(super.getLocation(), textureUnit);
    }
}
