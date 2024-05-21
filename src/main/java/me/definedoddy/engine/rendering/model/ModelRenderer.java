package me.definedoddy.engine.rendering.model;

import me.definedoddy.engine.entity.ModelEntity;
import me.definedoddy.engine.manager.GameManager;
import me.definedoddy.engine.rendering.camera.Camera;
import me.definedoddy.engine.rendering.config.RenderConfig;
import me.definedoddy.engine.rendering.lighting.Light;
import me.definedoddy.engine.rendering.object.mesh.Mesh;
import me.definedoddy.engine.rendering.object.model.Model;
import me.definedoddy.engine.scene.SceneManager;
import me.definedoddy.toolkit.debug.Debug;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelRenderer {
    private final ModelShader shader;
    private final Map<Mesh, List<ModelEntity>> entities = new HashMap<>();

    public ModelRenderer(ModelShader shader) {
        this.shader = shader;
    }

    public void render() {
        shader.bind();

        shader.getProjectionMatrix().loadMatrix(Camera.get().getProjectionMatrix());
        shader.getViewMatrix().loadMatrix(Camera.get().getViewMatrix());

        applyLighting();

        for (Map.Entry<Mesh, List<ModelEntity>> entry : entities.entrySet()) {
            Mesh mesh = entry.getKey();
            mesh.bind();

            for (ModelEntity entity : entry.getValue()) {
                Model model = entity.getModel();
                shader.getColour().loadColour(model.getMaterial().getTintColour());
                shader.getReflectivity().loadFloat(model.getMaterial().getReflectivity());

                if (model.getMaterial().isTransparent()) disableCulling();
                model.render(entity.getPosition(), entity.getRotation(), entity.getScale());
            }

            mesh.unbind();
            enableCulling();
        }

        shader.unbind();
    }

    private void applyLighting() {
        RenderConfig renderConfig = GameManager.getRenderEngine().getRenderConfig();
        List<Light> lights = SceneManager.getCurrentScene().getLights();

        int maxLights = renderConfig.getMaxLightsOnMesh();
        if (lights.size() > maxLights) {
            lights = lights.subList(0, maxLights);
            Debug.logError("Too many lights on mesh, only the first " + maxLights + " lights will be used");
        }

        shader.getNumLights().loadInt(lights.size());
        shader.getAmbientLight().loadFloat(renderConfig.getAmbientLight());

        // Load lighting data
        for (int i = 0; i < lights.size(); i++) {
            shader.getLightPositions().loadVec3(i, lights.get(i).getPosition());
            shader.getLightColours().loadColour(i, lights.get(i).getColour());
            shader.getLightAttenuations().loadVec3(i, lights.get(i).getAttenuation());
        }
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
        Mesh mesh = entity.getModel().getMesh();
        List<ModelEntity> batch = entities.computeIfAbsent(mesh, k -> new ArrayList<>());
        batch.add(entity);
    }

    public void removeEntity(ModelEntity entity) {
        Mesh mesh = entity.getModel().getMesh();
        List<ModelEntity> batch = entities.get(mesh);
        if (batch != null) {
            batch.remove(entity);
            if (batch.isEmpty()) {
                entities.remove(mesh);
            }
        }
    }

    public void stop() {
        shader.dispose();

        for (List<ModelEntity> batch : entities.values()) {
            for (ModelEntity entity : batch) {
                entity.getModel().dispose();
            }
        }
    }
}
