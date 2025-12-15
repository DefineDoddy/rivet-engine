package org.rivetengine.rendering;

import org.rivetengine.core.Game;
import org.rivetengine.debug.DebugRenderer;
import org.rivetengine.debug.shader.DebugShader;
import org.rivetengine.rendering.mesh.MeshRenderer;
import org.rivetengine.rendering.mesh.shader.MeshShader;
import org.rivetengine.rendering.skybox.SkyboxRenderer;
import org.rivetengine.rendering.skybox.shader.SkyboxShader;
import org.rivetengine.system.GameSystem;
import org.rivetengine.toolkit.memory.Disposable;

public class RenderSystem extends GameSystem implements Disposable {
    private final MeshRenderer meshRenderer = new MeshRenderer(MeshShader.create());
    private final DebugRenderer debugRenderer = new DebugRenderer(DebugShader.create());
    private final SkyboxRenderer skyboxRenderer = new SkyboxRenderer(SkyboxShader.create());

    @Override
    public void update(Game game, float dt) {
        skyboxRenderer.render(game);
        meshRenderer.render(game);
        debugRenderer.render(game);
    }

    @Override
    public void dispose() {
        meshRenderer.dispose();
        debugRenderer.dispose();
        skyboxRenderer.dispose();
    }
}
