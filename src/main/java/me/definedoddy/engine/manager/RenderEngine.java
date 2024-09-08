package me.definedoddy.engine.manager;

import me.definedoddy.engine.rendering.config.DefaultRenderConfig;
import me.definedoddy.engine.rendering.config.RenderConfig;
import me.definedoddy.engine.rendering.model.shader.ModelRenderer;
import me.definedoddy.engine.rendering.model.shader.ModelShader;
import me.definedoddy.engine.rendering.skybox.shader.SkyboxRenderer;
import me.definedoddy.engine.rendering.skybox.shader.SkyboxShader;
import me.definedoddy.engine.debug.Debug;
import me.definedoddy.engine.ui.shader.UIRenderer;
import me.definedoddy.engine.ui.shader.UIShader;

public class RenderEngine {
    private RenderConfig renderConfig = new DefaultRenderConfig();

    private ModelRenderer modelRenderer;
    private SkyboxRenderer skyboxRenderer;
    private UIRenderer uiRenderer;

    public void init() {
        modelRenderer = new ModelRenderer(ModelShader.create());
        skyboxRenderer = new SkyboxRenderer(SkyboxShader.create());
        uiRenderer = new UIRenderer(UIShader.create());
    }

    public void update() {
        modelRenderer.render();
        skyboxRenderer.render();
        Debug.render();
        uiRenderer.render();
    }

    public ModelRenderer getModelRenderer() {
        return modelRenderer;
    }

    public SkyboxRenderer getSkyboxRenderer() {
        return skyboxRenderer;
    }

    public UIRenderer getUIRenderer() {
        return uiRenderer;
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
        uiRenderer.stop();
    }
}
