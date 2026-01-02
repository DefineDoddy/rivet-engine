package org.rivetengine.core;

import org.rivetengine.entity.Entity;

import java.util.*;

public class Scene {
    private final Map<UUID, Entity> rootEntities = new HashMap<>();

    public void load() {
    }

    public void unload() {
    }

    public void spawn(Entity entity) {
        rootEntities.put(entity.id, entity);
    }

    public void remove(Entity entity) {
        rootEntities.remove(entity.id);
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

    public Entity getEntity(UUID id) {
        return rootEntities.get(id);
    }

    public Entity getEntity(String name) {
        return getEntity(name, true);
    }

    public Entity getEntity(String name, boolean recursive) {
        for (Entity root : rootEntities.values()) {
            if (name != null && name.equals(root.name)) {
                return root;
            }
            if (recursive) {
                Entity found = root.getChild(name, true);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }
}
