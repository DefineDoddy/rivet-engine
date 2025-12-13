package org.rivetengine.rendering.skybox;

import org.rivetengine.core.Assets;
import org.rivetengine.core.Game;
import org.rivetengine.entity.components.rendering.Skybox;
import org.rivetengine.rendering.RenderUtils;
import org.rivetengine.rendering.skybox.shader.SkyboxShader;
import org.rivetengine.toolkit.memory.Disposable;
import org.joml.Matrix4f;

public class SkyboxRenderer implements Disposable {
    private final SkyboxShader shader;

    public SkyboxRenderer(SkyboxShader shader) {
        this.shader = shader;
    }

    public void render(Game game, Skybox skybox) {
        if (skybox == null) {
            return;
        }

        shader.bind();
        Matrix4f[] cameraMatrices = RenderUtils.createCameraMatrices(game);

        shader.projectionMatrix.loadMatrix(cameraMatrices[0]);
        shader.viewMatrix.loadMatrix(modifyViewMatrix(cameraMatrices[1]));
        shader.rotation.loadFloat(skybox.rotation);

        Assets.get(skybox.cubeMap).render();
        shader.unbind();
    }

    private Matrix4f modifyViewMatrix(Matrix4f viewMatrix) {
        viewMatrix.m30(0);
        viewMatrix.m31(0);
        viewMatrix.m32(0);
        return viewMatrix;
    }

    @Override
    public void dispose() {
        shader.dispose();
    }
}
