package org.rivetengine.physics;

import org.joml.Vector3f;
import org.rivetengine.core.Scene;
import org.rivetengine.entity.Entity;
import org.rivetengine.entity.components.Tags;
import org.rivetengine.physics.collision.raycast.RayCast;
import org.rivetengine.physics.collision.raycast.RayCastHit;

import java.util.function.Predicate;

public class Physics {
    public static Vector3f gravity = new Vector3f(0, -9.81f, 0);
    public static final float TIME_STEP = 1f / 60f;
    public static final float RAYCAST_STEP = 0.1f;

    public static RayCastHit rayCast(Scene scene, Vector3f origin, Vector3f direction) {
        return new RayCast(scene, origin, direction).cast();
    }

    public static RayCastHit rayCast(Scene scene, Vector3f origin, Vector3f direction, float maxDistance) {
        return new RayCast(scene, origin, direction, maxDistance).cast();
    }

    public static RayCastHit rayCast(Scene scene, Vector3f origin, Vector3f direction, float maxDistance,
            Predicate<Entity> filter) {
        return new RayCast(scene, origin, direction, maxDistance).withFilter(filter).cast();
    }

    public static RayCastHit rayCast(Scene scene, Vector3f origin, Vector3f direction, float maxDistance,
            Entity... ignored) {
        return rayCast(scene, origin, direction, maxDistance, entity -> {
            for (Entity e : ignored) {
                if (e == entity)
                    return false;
            }
            return true;
        });
    }

    public static RayCastHit rayCast(Scene scene, Vector3f origin, Vector3f direction, float maxDistance,
            String... ignoredTags) {
        return rayCast(scene, origin, direction, maxDistance, entity -> {
            Tags tags = entity.getComponent(Tags.class);
            if (tags == null)
                return true;
            for (String tag : ignoredTags) {
                if (tags.hasTag(tag))
                    return false;
            }
            return true;
        });
    }
}
