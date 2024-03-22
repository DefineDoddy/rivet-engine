package me.definedoddy.engine.rendering.shader;

import org.lwjgl.opengl.GL20;

import java.awt.*;

public class UniformColour extends Uniform {
    public UniformColour(String name) {
        super(name);
    }

    public void loadColour(Color color) {
        loadColour(color.getRed(), color.getGreen(), color.getBlue());
    }

    public void loadColour(float r, float g, float b) {
        GL20.glUniform3f(super.getLocation(), r, g, b);
    }
}
