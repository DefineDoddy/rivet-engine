package org.rivetengine.rendering.shader.uniform;

import org.rivetengine.debug.Debug;
import org.lwjgl.opengl.GL20;

public abstract class Uniform {
    public final String name;
    public int programId;
    private int location = -1;

    protected Uniform(String name) {
        this.name = name;
    }

    public int getLocation() {
        if (location == -1) {
            location = GL20.glGetUniformLocation(programId, name);

            if (location == -1) {
                Debug.logError("Could not find uniform variable: " + name);
            }
        }

        return location;
    }
}
