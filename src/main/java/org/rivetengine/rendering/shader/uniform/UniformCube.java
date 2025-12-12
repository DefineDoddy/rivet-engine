package org.rivetengine.rendering.shader.uniform;

import org.lwjgl.opengl.GL20;

public class UniformCube extends Uniform {
    public UniformCube(String name) {
        super(name);
    }

    public void loadTexUnits(int[] unit) {
        for (int i = 0; i < unit.length; i++) {
            GL20.glUniform1i(getLocationOf(i), unit[i]);
        }
    }

    private int getLocationOf(int index) {
        return GL20.glGetUniformLocation(getProgramId(), getName() + "[" + index + "]");
    }
}
