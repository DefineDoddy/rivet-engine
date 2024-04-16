package me.definedoddy.engine.rendering.entity;

import me.definedoddy.engine.entity.ModelEntity;
import me.definedoddy.engine.rendering.camera.Camera;

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

        for (ModelEntity entity : entities) {
            shader.getColour().loadColour(entity.getModel().getColour());
            entity.getModel().render(entity);
        }

        shader.unbind();
    }

    public EntityShader getShader() {
        return shader;
    }

    public void addEntityToRender(ModelEntity entity) {
        entities.add(entity);
    }
}
