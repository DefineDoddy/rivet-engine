package me.definedoddy.engine.rendering.shader;

import me.definedoddy.toolkit.debug.DebugLog;
import org.lwjgl.opengl.GL20;

public abstract class Uniform {
    private final String name;
    private int location;

    protected Uniform(String name) {
        this.name = name;
    }

    public void storeUniformLocation(int programID) {
        location = GL20.glGetUniformLocation(programID, name);
        if (location == -1) DebugLog.error("No uniform variable called %s found!", name);
    }

    public String getName() {
        return name;
    }

    public int getLocation() {
        return location;
    }
}
