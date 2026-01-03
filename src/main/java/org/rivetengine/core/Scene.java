package org.rivetengine.core;

import org.rivetengine.entity.Entity;
import org.rivetengine.physics.Physics;
import org.rivetengine.physics.collision.raycast.RayCastHit;
import org.joml.Vector3f;
import org.rivetengine.toolkit.memory.Handle;

import java.util.*;
import java.util.function.Predicate;

public class Scene {
    private final Map<UUID, Entity> rootEntities = new HashMap<>();

    public RayCastHit rayCast(Vector3f origin, Vector3f direction) {
        return Physics.rayCast(this, origin, direction);
    }

    public RayCastHit rayCast(Vector3f origin, Vector3f direction, float maxDistance) {
        return Physics.rayCast(this, origin, direction, maxDistance);
    }

    public RayCastHit rayCast(Vector3f origin, Vector3f direction, float maxDistance, Predicate<Entity> filter) {
        return Physics.rayCast(this, origin, direction, maxDistance, filter);
    }

    public RayCastHit rayCast(Vector3f origin, Vector3f direction, float maxDistance, Entity... ignored) {
        return Physics.rayCast(this, origin, direction, maxDistance, ignored);
    }

    public RayCastHit rayCast(Vector3f origin, Vector3f direction, float maxDistance, String... ignoredTags) {
        return Physics.rayCast(this, origin, direction, maxDistance, ignoredTags);
    }

    public void load() {
    }

    public void unload() {
    }

    public void spawn(Entity entity) {
        rootEntities.put(entity.id, entity);
    }

    public void spawn(Handle<Entity> handle) {
        Entity entity = Assets.get(handle);
        if (entity != null) {
            spawn(entity);
        }
    }

    public void remove(Entity entity) {
        rootEntities.remove(entity.id);
    }

    public void remove(Handle<Entity> handle) {
        Entity entity = Assets.get(handle);
        if (entity != null) {
            remove(entity);
        }
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
