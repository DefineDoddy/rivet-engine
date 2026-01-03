package org.rivetengine.rendering;

import org.rivetengine.core.Game;
import org.rivetengine.debug.DebugRenderer;
import org.rivetengine.debug.shader.DebugShader;
import org.rivetengine.rendering.mesh.MeshRenderer;
import org.rivetengine.rendering.mesh.shader.MeshShader;
import org.rivetengine.rendering.postprocessing.PostProcessingRenderer;
import org.rivetengine.rendering.skybox.SkyboxRenderer;
import org.rivetengine.rendering.skybox.shader.SkyboxShader;
import org.rivetengine.system.GameSystem;
import org.rivetengine.toolkit.memory.Disposable;
import org.lwjgl.opengl.GL11;

public class RenderSystem extends GameSystem implements Disposable {
    private final MeshRenderer meshRenderer = new MeshRenderer(MeshShader.create());
    private final DebugRenderer debugRenderer = new DebugRenderer(DebugShader.create());
    private final SkyboxRenderer skyboxRenderer = new SkyboxRenderer(SkyboxShader.create());
    private PostProcessingRenderer postProcessingRenderer;

    @Override
    public void update(Game game, float dt) {
        if (postProcessingRenderer == null) {
            postProcessingRenderer = new PostProcessingRenderer(game);
        }

        boolean usePostProcessing = !postProcessingRenderer.getActiveEffects().isEmpty();

        if (usePostProcessing) {
            postProcessingRenderer.bindFramebuffer();
        }

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        skyboxRenderer.render(game);
        meshRenderer.render(game);
        debugRenderer.render(game);

        if (usePostProcessing) {
            postProcessingRenderer.unbindFramebuffer();
            postProcessingRenderer.render(postProcessingRenderer.getOutputTextureId());
        }
    }

    @Override
    public void dispose() {
        meshRenderer.dispose();
        debugRenderer.dispose();
        skyboxRenderer.dispose();

        if (postProcessingRenderer != null) {
            postProcessingRenderer.dispose();
        }
    }
}
