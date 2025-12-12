package org.rivetengine.rendering;

import org.rivetengine.debug.Debug;
import org.rivetengine.entity.components.rendering.Camera;
import org.rivetengine.rendering.config.DefaultRenderConfig;
import org.rivetengine.rendering.config.RenderConfig;
import org.rivetengine.rendering.mesh.shader.ModelRenderer;
import org.rivetengine.rendering.mesh.shader.ModelShader;
import org.rivetengine.rendering.skybox.shader.SkyboxRenderer;
import org.rivetengine.rendering.skybox.shader.SkyboxShader;
import org.rivetengine.ui.shader.UIRenderer;
import org.rivetengine.ui.shader.UIShader;

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
