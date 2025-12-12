package org.rivetengine.rendering.skybox.shader;

import org.rivetengine.entity.components.rendering.Camera;
import org.rivetengine.entity.components.rendering.Skybox;

import org.joml.Matrix4f;

public class SkyboxRenderer {
    private final SkyboxShader shader;
    private Skybox skybox;

    public SkyboxRenderer(SkyboxShader shader) {
        this.shader = shader;
    }

    public void render() {
        if (skybox == null) {
            return;
        }

        shader.bind();

        shader.getProjectionMatrix().loadMatrix(Camera.getActive().getProjectionMatrix());
        shader.getViewMatrix().loadMatrix(getViewMatrix());
        shader.getRotation().loadFloat(skybox.getRotation());

        skybox.getCubeMap().render();

        shader.unbind();
    }

    private Matrix4f getViewMatrix() {
        Matrix4f viewMatrix = new Matrix4f(Camera.getActive().getViewMatrix());
        viewMatrix.m30(0);
        viewMatrix.m31(0);
        viewMatrix.m32(0);
        return viewMatrix;
    }

    public void setSkybox(Skybox skybox) {
        this.skybox = skybox;
    }

    public Skybox getSkybox() {
        return skybox;
    }

    public void stop() {
        shader.dispose();

        if (skybox != null) {
            skybox.dispose();
        }
    }
}
