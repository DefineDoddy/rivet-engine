package me.definedoddy.engine.rendering.shader;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL20;

public class UniformMatrix4f extends Uniform {
    public UniformMatrix4f(String name) {
        super(name);
    }

    public void loadMatrix(Matrix4f matrix) {
        GL20.glUniformMatrix4fv(super.getLocation(), false, matrix.get(new float[16]));
    }
}
