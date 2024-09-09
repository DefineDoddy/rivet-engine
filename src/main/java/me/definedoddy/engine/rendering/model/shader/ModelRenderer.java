package me.definedoddy.engine.rendering.model.shader;

import me.definedoddy.engine.entity.ModelEntity;
import me.definedoddy.engine.manager.GameManager;
import me.definedoddy.engine.rendering.camera.Camera;
import me.definedoddy.engine.rendering.config.RenderConfig;
import me.definedoddy.engine.rendering.lighting.Light;
import me.definedoddy.engine.rendering.model.mesh.Mesh;
import me.definedoddy.engine.rendering.model.model.MeshMap;
import me.definedoddy.engine.rendering.model.model.Model;
import me.definedoddy.engine.rendering.texture.Material;
import me.definedoddy.engine.scene.SceneManager;
import me.definedoddy.engine.debug.Debug;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelRenderer {
    private final ModelShader shader;
    private final Map<MeshMap, List<ModelEntity>> batches = new HashMap<>();

    public ModelRenderer(ModelShader shader) {
        this.shader = shader;
    }

    public void render() {
        shader.bind();

        shader.getProjectionMatrix().loadMatrix(Camera.get().getProjectionMatrix());
        shader.getViewMatrix().loadMatrix(Camera.get().getViewMatrix());

        applyLighting();

        for (Map.Entry<MeshMap, List<ModelEntity>> entry : batches.entrySet()) {
            MeshMap meshMap = entry.getKey();
            List<ModelEntity> entities = entry.getValue();

            for (Map.Entry<Mesh, Material> data : meshMap.getData().entrySet()) {
                Mesh mesh = data.getKey();
                mesh.bind();

                for (ModelEntity entity : entities) {
                    Model model = entity.getModel();
                    if (model.getMaterial().isTransparent()) disableCulling();
                    model.renderMesh(mesh.getId(), entity.getPosition(), entity.getRotation(), entity.getScale());
                }

                mesh.unbind();
                enableCulling();
            }
        }

        shader.unbind();
    }

    private void applyLighting() {
        RenderConfig renderConfig = GameManager.getRenderEngine().getRenderConfig();
        int maxLights = renderConfig.getMaxLights();

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
        // Ensure that entities with the same mesh are rendered together
        MeshMap map = entity.getModel().getMeshMap();
        List<ModelEntity> batch = batches.computeIfAbsent(map, k -> new ArrayList<>());
        batch.add(entity);
    }

    public void removeEntity(ModelEntity entity) {
        MeshMap map = entity.getModel().getMeshMap();
        List<ModelEntity> batch = batches.get(map);
        if (batch != null) {
            batch.remove(entity);
            if (batch.isEmpty()) {
                batches.remove(map);
            }
        }
    }

    public void stop() {
        shader.dispose();

        for (List<ModelEntity> entities : batches.values()) {
            for (ModelEntity entity : entities) {
                entity.dispose();
            }
        }
    }
}
