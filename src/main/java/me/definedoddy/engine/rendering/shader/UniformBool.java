package me.definedoddy.engine.rendering.shader;

import me.definedoddy.engine.rendering.shader.Uniform;
import org.lwjgl.opengl.GL20;

public class UniformBool extends Uniform {
    public UniformBool(String name) {
        super(name);
    }

    public void loadBoolean(boolean value) {
        GL20.glUniform1i(super.getLocation(), value ? 1 : 0);
    }
}
