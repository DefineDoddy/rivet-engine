package me.definedoddy.engine.manager;

import me.definedoddy.engine.rendering.config.DefaultRenderConfig;
import me.definedoddy.engine.rendering.config.RenderConfig;
import me.definedoddy.engine.rendering.model.ModelRenderer;
import me.definedoddy.engine.rendering.model.ModelShader;

public class RenderEngine {
    private RenderConfig renderConfig = new DefaultRenderConfig();
    private ModelRenderer modelRenderer;

    public void init() {
        modelRenderer = new ModelRenderer(ModelShader.create());
    }

    public void update() {
        modelRenderer.render();
    }

    public ModelRenderer getModelRenderer() {
        return modelRenderer;
    }

    public void setRenderConfig(RenderConfig renderConfig) {
        this.renderConfig = renderConfig;
    }

    public RenderConfig getRenderConfig() {
        return renderConfig;
    }
}
