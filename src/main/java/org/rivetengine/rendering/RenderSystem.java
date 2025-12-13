package org.rivetengine.rendering;

import java.util.List;

import org.rivetengine.core.Game;
import org.rivetengine.entity.Entity;
import org.rivetengine.entity.components.rendering.Mesh3d;
import org.rivetengine.entity.components.rendering.lighting.Light;
import org.rivetengine.rendering.mesh.MeshRenderer;
import org.rivetengine.rendering.mesh.shader.MeshShader;
import org.rivetengine.system.GameSystem;
import org.rivetengine.toolkit.memory.Disposable;

public class RenderSystem extends GameSystem implements Disposable {
    public static final int MAX_LIGHTS = 100;
    public static final float AMBIENT_LIGHT = 0.1f;
    public static final boolean USE_MIPMAPPING = true;
    public static final float LOD_BIAS = -0.4f;

    private final MeshRenderer meshRenderer = new MeshRenderer(MeshShader.create());

    @Override
    public void update(Game game, float dt) {
        List<Entity> entities = game.getActiveScene().getAllEntities();

        List<Entity> lightEntities = entities.stream()
                .filter(e -> e.hasComponent(Light.class))
                .toList();

        List<Entity> meshEntities = entities.stream()
                .filter(e -> e.hasComponent(Mesh3d.class))
                .toList();

        meshRenderer.applyLighting(lightEntities);
        meshRenderer.renderMeshes(game, meshEntities);
    }

    @Override
    public void dispose() {
        meshRenderer.dispose();
    }
}
