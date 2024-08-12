package me.definedoddy.engine.rendering.shader.uniform;

import org.joml.Vector2f;
import org.lwjgl.opengl.GL20;

public class UniformVec2f extends Uniform {
    public UniformVec2f(String name) {
        super(name);
    }

    public void loadVec2(Vector2f vector) {
        loadVec2(vector.x(), vector.y());
    }

    public void loadVec2(float x, float y) {
        GL20.glUniform2f(super.getLocation(), x, y);
    }
}
