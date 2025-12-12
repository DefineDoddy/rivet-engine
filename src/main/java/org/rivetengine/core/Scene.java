package org.rivetengine.core;

import org.rivetengine.entity.Entity;
import org.rivetengine.entity.components.Name;

import java.util.*;

public class Scene {
    private final Map<UUID, Entity> rootEntities = new HashMap<>();

    public void load() {
    }

    public void unload() {
    }

    public void addEntity(Entity entity) {
        rootEntities.put(entity.getId(), entity);
    }

    public void removeEntity(Entity entity) {
        rootEntities.remove(entity.getId());
    }

    public Entity getEntity(UUID id) {
        return rootEntities.get(id);
    }

    public List<Entity> getRootEntities() {
        return rootEntities.values().stream().toList();
    }

    public List<Entity> getAllEntities() {
        List<Entity> all = new ArrayList<>();
        for (Entity root : rootEntities.values()) {
            collectEntities(root, all);
        }
        return all;
    }

    private void collectEntities(Entity entity, List<Entity> list) {
        list.add(entity);

        for (Entity child : entity.getChildren()) {
            collectEntities(child, list);
        }
    }

    public Entity findEntity(String name) {
        for (Entity child : rootEntities.values()) {
            Name n = child.getComponent(Name.class);

            if (n != null && n.getName().equals(name)) {
                return child;
            }
        }
        return null;
    }

    public Entity findEntityRecursive(String name) {
        Entity found = findEntity(name);

        if (found != null) {
            return found;
        }

        for (Entity child : rootEntities.values()) {
            found = child.findChildRecursive(name);

            if (found != null) {
                return found;
            }
        }

        return null;
    }
}
