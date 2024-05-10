package me.definedoddy.engine.rendering.entity;

import me.definedoddy.engine.entity.ModelEntity;
import me.definedoddy.engine.manager.GameManager;
import me.definedoddy.engine.rendering.camera.Camera;
import me.definedoddy.engine.rendering.lighting.Light;
import me.definedoddy.engine.scene.SceneManager;
import org.joml.Vector3f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EntityRenderer {
    private final EntityShader shader;
    private final List<ModelEntity> entities = new ArrayList<>();

    public EntityRenderer(EntityShader shader) {
        this.shader = shader;
    }

    public void render() {
        shader.bind();

        shader.getProjectionMatrix().loadMatrix(Camera.get().getProjectionMatrix());
        shader.getViewMatrix().loadMatrix(Camera.get().getViewMatrix());

        applyLighting();

        for (ModelEntity entity : entities) {
            shader.getColour().loadColour(entity.getModel().getMaterial().getTintColour());
            shader.getReflectivity().loadFloat(entity.getModel().getMaterial().getReflectivity());
            entity.getModel().render(entity);
        }

        shader.unbind();
    }

    private void applyLighting() {
        List<Light> lights = SceneManager.getCurrentScene().getLights();
        int maxLightsOnEntity = GameManager.getRenderEngine().getRenderConfig().getMaxLightsOnEntity();
        shader.getNumLights().loadInt(lights.size());

        for (int i = 0; i < maxLightsOnEntity; i++) {
            if (i < lights.size()) {
                shader.getLightPositions().loadVec3(i, lights.get(i).getPosition());
                shader.getLightColours().loadColour(i, lights.get(i).getColour());
            } else {
                shader.getLightPositions().loadVec3(i, new Vector3f());
                shader.getLightColours().loadColour(i, Color.BLACK);
            }
        }
    }

    public EntityShader getShader() {
        return shader;
    }

    public void addEntity(ModelEntity entity) {
        entities.add(entity);
    }

    public void removeEntity(ModelEntity entity) {
        entities.remove(entity);
    }
}
