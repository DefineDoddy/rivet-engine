package me.definedoddy.engine.manager;

import me.definedoddy.engine.rendering.config.DefaultRenderConfig;
import me.definedoddy.engine.rendering.config.RenderConfig;
import me.definedoddy.engine.rendering.entity.EntityRenderer;
import me.definedoddy.engine.rendering.entity.EntityShader;

public class RenderEngine {
    private RenderConfig renderConfig = new DefaultRenderConfig();
    private EntityRenderer entityRenderer;

    public RenderEngine() {

    }

    public void init() {
        entityRenderer = new EntityRenderer(EntityShader.create());
    }

    public void update() {
        entityRenderer.render();
    }

    public EntityRenderer getEntityRenderer() {
        return entityRenderer;
    }

    public void setRenderConfig(RenderConfig renderConfig) {
        this.renderConfig = renderConfig;
    }

    public RenderConfig getRenderConfig() {
        return renderConfig;
    }
}
