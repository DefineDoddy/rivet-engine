package org.rivetengine.rendering;

import me.definedoddy.engine.debug.Debug;
import me.definedoddy.engine.entity.components.rendering.Camera;
import me.definedoddy.engine.rendering.config.DefaultRenderConfig;
import me.definedoddy.engine.rendering.config.RenderConfig;
import me.definedoddy.engine.rendering.mesh.shader.ModelRenderer;
import me.definedoddy.engine.rendering.mesh.shader.ModelShader;
import me.definedoddy.engine.rendering.skybox.shader.SkyboxRenderer;
import me.definedoddy.engine.rendering.skybox.shader.SkyboxShader;
import me.definedoddy.engine.ui.shader.UIRenderer;
import me.definedoddy.engine.ui.shader.UIShader;

public class RenderEngine {
    private RenderConfig renderConfig = new DefaultRenderConfig();

    private ModelRenderer modelRenderer;
    private SkyboxRenderer skyboxRenderer;

    public void init() {
        modelRenderer = new ModelRenderer(ModelShader.create());
        skyboxRenderer = new SkyboxRenderer(SkyboxShader.create());
    }

    public void update() {
        if (Camera.getActive() != null) {
            modelRenderer.render();
            skyboxRenderer.render();
            Debug.render();
        }
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
