package me.definedoddy.engine.scene;

import me.definedoddy.engine.entity.Entity;
import me.definedoddy.engine.entity.ModelEntity;
import me.definedoddy.engine.manager.GameManager;
import me.definedoddy.engine.rendering.lighting.Light;
import me.definedoddy.engine.rendering.shader.model.ModelRenderer;
import me.definedoddy.engine.rendering.skybox.Skybox;

import java.util.*;

public class Scene {
    private final Map<UUID, Entity> entities = new HashMap<>();
    private final List<Light> lights = new ArrayList<>();

    private final ModelRenderer entityRenderer = GameManager.getRenderEngine().getModelRenderer();

    public void update() {
        entities.values().forEach(Entity::update);
        entities.values().removeIf(Entity::isRemoved);
    }

    public void load() {}
    public void unload() {}

    public void addEntity(Entity entity) {
        entities.put(entity.getUuid(), entity);

        if (entity instanceof ModelEntity modelEntity) {
            entityRenderer.addEntity(modelEntity);
        }
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity.getUuid());

        if (entity instanceof ModelEntity modelEntity) {
            entityRenderer.removeEntity(modelEntity);
        }
    }

    public Entity getEntity(UUID uuid) {
        return entities.get(uuid);
    }

    public List<Entity> getEntities() {
        return entities.values().stream().toList();
    }

    public void addLight(Light light) {
        lights.add(light);
    }

    public void removeLight(Light light) {
        lights.remove(light);
    }

    public List<Light> getLights() {
        return lights;
    }

    public void setSkybox(Skybox skybox) {
        GameManager.getRenderEngine().getSkyboxRenderer().setSkybox(skybox);
    }

    public Skybox getSkybox() {
        return GameManager.getRenderEngine().getSkyboxRenderer().getSkybox();
    }
}
