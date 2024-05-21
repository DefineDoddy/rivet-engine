package me.definedoddy.engine.rendering.skybox;

import me.definedoddy.engine.rendering.camera.Camera;
import org.joml.Matrix4f;

public class SkyboxRenderer {
    private final SkyboxShader shader;
    private Skybox skybox;

    public SkyboxRenderer(SkyboxShader shader) {
        this.shader = shader;
    }

    public void render() {
        if (skybox == null) return;
        shader.bind();

        shader.getProjectionMatrix().loadMatrix(Camera.get().getProjectionMatrix());
        shader.getViewMatrix().loadMatrix(getViewMatrix());
        shader.getRotation().loadFloat(skybox.getRotation());

        skybox.getCubemap().render();

        shader.unbind();
    }

    private Matrix4f getViewMatrix() {
        Matrix4f viewMatrix = new Matrix4f(Camera.get().getViewMatrix());
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
