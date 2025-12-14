package org.rivetengine.rendering;

import java.util.List;

import org.rivetengine.core.Game;
import org.rivetengine.debug.DebugRenderer;
import org.rivetengine.debug.shader.DebugShader;
import org.rivetengine.entity.Entity;
import org.rivetengine.entity.components.rendering.Mesh3d;
import org.rivetengine.entity.components.rendering.lighting.Light;
import org.rivetengine.rendering.mesh.MeshRenderer;
import org.rivetengine.rendering.mesh.shader.MeshShader;
import org.rivetengine.system.GameSystem;
import org.rivetengine.toolkit.memory.Disposable;

public class RenderSystem extends GameSystem implements Disposable {
    private final MeshRenderer meshRenderer = new MeshRenderer(MeshShader.create());
    private final DebugRenderer debugRenderer = new DebugRenderer(DebugShader.create());

    @Override
    public void update(Game game, float dt) {
        List<Entity> lightEntities = getEntitiesWith(game, Light.class);
        List<Entity> meshEntities = getEntitiesWith(game, Mesh3d.class);

        meshRenderer.applyLighting(lightEntities);
        meshRenderer.renderMeshes(game, meshEntities);

        debugRenderer.render(game);
    }

    @Override
    public void dispose() {
        meshRenderer.dispose();
        debugRenderer.dispose();
    }
}
