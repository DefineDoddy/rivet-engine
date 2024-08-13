package me.definedoddy.engine.manager;

import me.definedoddy.engine.rendering.config.DefaultRenderConfig;
import me.definedoddy.engine.rendering.config.RenderConfig;
import me.definedoddy.engine.rendering.shader.model.ModelRenderer;
import me.definedoddy.engine.rendering.shader.model.ModelShader;
import me.definedoddy.engine.rendering.skybox.SkyboxRenderer;
import me.definedoddy.engine.rendering.skybox.SkyboxShader;
import me.definedoddy.toolkit.debug.Debug;

public class RenderEngine {
    private RenderConfig renderConfig = new DefaultRenderConfig();

    private ModelRenderer modelRenderer;
    private SkyboxRenderer skyboxRenderer;

    public void init() {
        modelRenderer = new ModelRenderer(ModelShader.create());
        skyboxRenderer = new SkyboxRenderer(SkyboxShader.create());
    }

    public void update() {
        modelRenderer.render();
        skyboxRenderer.render();
        Debug.render();
    }

    public ModelRenderer getModelRenderer() {
        return modelRenderer;
    }

    public SkyboxRenderer getSkyboxRenderer() {
        return skyboxRenderer;
    }

    public void setRenderConfig(RenderConfig renderConfig) {
        this.renderConfig = renderConfig;
    }

    public RenderConfig getRenderConfig() {
        return renderConfig;
    }

    public void stop() {
        modelRenderer.stop();
        skyboxRenderer.stop();
    }
}
