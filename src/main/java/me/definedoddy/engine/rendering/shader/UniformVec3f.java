package me.definedoddy.engine.rendering.shader;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL20;

public class UniformVec3f extends Uniform {
    public UniformVec3f(String name) {
        super(name);
    }

    public void loadVec3(Vector3f vector) {
        loadVec3(vector.x(), vector.y(), vector.z());
    }

    public void loadVec3(float x, float y, float z) {
        GL20.glUniform3f(super.getLocation(), x, y, z);
    }
}
