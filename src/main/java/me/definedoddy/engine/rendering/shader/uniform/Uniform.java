package me.definedoddy.engine.rendering.shader.uniform;

import me.definedoddy.toolkit.debug.Debug;
import org.lwjgl.opengl.GL20;

public abstract class Uniform {
    private final String name;
    private int programID;
    private int location = -1;

    protected Uniform(String name) {
        this.name = name;
    }

    public void setProgramId(int programID) {
        this.programID = programID;
    }

    public String getName() {
        return name;
    }

    public int getProgramId() {
        return programID;
    }

    public int getLocation() {
        if (location == -1) {
            location = GL20.glGetUniformLocation(programID, name);
            if (location == -1) Debug.logError("Could not find uniform variable: " + name);
        }
        return location;
    }
}
