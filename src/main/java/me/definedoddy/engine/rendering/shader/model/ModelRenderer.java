package me.definedoddy.engine.rendering.shader.model;

import me.definedoddy.engine.entity.ModelEntity;
import me.definedoddy.engine.manager.GameManager;
import me.definedoddy.engine.rendering.camera.Camera;
import me.definedoddy.engine.rendering.config.RenderConfig;
import me.definedoddy.engine.rendering.lighting.Light;
import me.definedoddy.engine.rendering.object.model.Model;
import me.definedoddy.engine.scene.SceneManager;
import me.definedoddy.toolkit.debug.Debug;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class ModelRenderer {
    private final ModelShader shader;
    private final List<ModelEntity> entities = new ArrayList<>();

    public ModelRenderer(ModelShader shader) {
        this.shader = shader;
    }

    public void render() {
        shader.bind();

        shader.getProjectionMatrix().loadMatrix(Camera.get().getProjectionMatrix());
        shader.getViewMatrix().loadMatrix(Camera.get().getViewMatrix());

        applyLighting();

        for (ModelEntity entity : entities) {
            Model model = entity.getModel();
            if (model.getMaterial().isTransparent()) disableCulling();
            model.render(entity.getPosition(), entity.getRotation(), entity.getScale());
        }

        shader.unbind();
    }

    private void applyLighting() {
        RenderConfig renderConfig = GameManager.getRenderEngine().getRenderConfig();
        int maxLights = renderConfig.getMaxDirectionalLights();

        List<Light> lights = SceneManager.getCurrentScene().getLights();

        if (lights.size() > maxLights) {
            Debug.logError("Too many lights in scene, only " + maxLights + " lights will be used");
            lights = lights.subList(0, maxLights);
        }

        for (int i = 0; i < lights.size(); i++) {
            Light light = lights.get(i);
            shader.getLights().loadLight(i, light);
        }

        shader.getLightCount().loadInt(lights.size());
    }

    private void enableCulling() {
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
    }

    private void disableCulling() {
        GL11.glDisable(GL11.GL_CULL_FACE);
    }

    public ModelShader getShader() {
        return shader;
    }

    public void addEntity(ModelEntity entity) {
        entities.add(entity);
    }

    public void removeEntity(ModelEntity entity) {
        entities.remove(entity);
    }

    public void stop() {
        shader.dispose();

        for (ModelEntity entity : entities) {
            entity.getModel().dispose();
        }
    }
}
