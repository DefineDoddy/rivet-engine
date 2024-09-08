package me.definedoddy.engine.scene;

import me.definedoddy.engine.entity.Entity;
import me.definedoddy.engine.entity.ModelEntity;
import me.definedoddy.engine.manager.GameManager;
import me.definedoddy.engine.rendering.lighting.Light;
import me.definedoddy.engine.rendering.model.shader.ModelRenderer;
import me.definedoddy.engine.scene.environment.Environment;

import java.util.*;

public class Scene {
    private final Map<UUID, Entity> entities = new HashMap<>();
    private final List<Light> lights = new ArrayList<>();

    private final ModelRenderer entityRenderer = GameManager.getRenderEngine().getModelRenderer();
    private final Environment environment = new Environment(this);
    private boolean loaded = false;

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

        entity.init();
    }

    public void removeEntity(Entity entity) {
        entity.remove();

        if (entity instanceof ModelEntity modelEntity) {
            entityRenderer.removeEntity(modelEntity);
        }

        entities.remove(entity.getUuid());
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

    void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public boolean isLoaded() {
        return loaded;
    }
}
