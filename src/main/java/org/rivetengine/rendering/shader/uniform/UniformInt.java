package org.rivetengine.rendering.shader.uniform;

import org.lwjgl.opengl.GL20;

public class UniformInt extends Uniform {
    public UniformInt(String name) {
        super(name);
    }

    public void loadInt(int value) {
        GL20.glUniform1i(super.getLocation(), value);
    }
}
