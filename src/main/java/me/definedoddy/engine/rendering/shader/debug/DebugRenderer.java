package me.definedoddy.engine.rendering.shader.debug;

import me.definedoddy.engine.rendering.camera.Camera;

public class DebugRenderer {
    private final DebugShader shader;

    public DebugRenderer(DebugShader shader) {
        this.shader = shader;
    }

    public void preRender() {
        shader.bind();

        shader.getProjectionMatrix().loadMatrix(Camera.get().getProjectionMatrix());
        shader.getViewMatrix().loadMatrix(Camera.get().getViewMatrix());

        /*// Position attribute
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
        GL20.glEnableVertexAttribArray(0);

        // Color attribute (make sure the location matches the shader's location)
        GL20.glVertexAttribPointer(1, 3, GL11.GL_FLOAT, false, 0, 0);
        GL20.glEnableVertexAttribArray(1);*/
    }

    public void postRender() {
        shader.unbind();
    }

    public DebugShader getShader() {
        return shader;
    }

    public void stop() {
        shader.dispose();
    }
}
