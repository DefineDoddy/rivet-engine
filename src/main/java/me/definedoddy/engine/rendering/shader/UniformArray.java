package me.definedoddy.engine.rendering.shader;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL20;

import java.awt.*;

public class UniformArray extends Uniform {
    private final Type type;

    public UniformArray(String name, Type type) {
        super(name);
        this.type = type;
    }

    public void loadVec3s(Vector3f[] values) {
        if (type != Type.VEC3F) {
            throw new IllegalArgumentException("Cannot load Vec3 values into a non-VEC3F uniform");
        }

        for (int i = 0; i < values.length; i++) {
            GL20.glUniform3f(getLocationOf(i), values[i].x, values[i].y, values[i].z);
        }
    }

    public void loadVec3(int index, Vector3f value) {
        if (type != Type.VEC3F) {
            throw new IllegalArgumentException("Cannot load Vec3 values into a non-VEC3F uniform");
        }

        GL20.glUniform3f(getLocationOf(index), value.x, value.y, value.z);
    }

    public void loadColours(Color[] values) {
        if (type != Type.COLOUR) {
            throw new IllegalArgumentException("Cannot load Colour values into a non-COLOUR uniform");
        }

        for (int i = 0; i < values.length; i++) {
            GL20.glUniform3f(getLocationOf(i),
                    values[i].getRed() / 255f,
                    values[i].getGreen() / 255f,
                    values[i].getBlue() / 255f
            );
        }
    }

    public void loadColour(int index, Color value) {
        if (type != Type.COLOUR) {
            throw new IllegalArgumentException("Cannot load Colour values into a non-COLOUR uniform");
        }

        GL20.glUniform3f(getLocationOf(index),
                value.getRed() / 255f,
                value.getGreen() / 255f,
                value.getBlue() / 255f
        );
    }

    private int getLocationOf(int index) {
        return GL20.glGetUniformLocation(getProgramID(), getName() + "[" + index + "]");
    }

    public enum Type {
        VEC3F, COLOUR
    }
}
