package org.rivetengine.physics.collision.raycast;

import org.rivetengine.physics.Physics;
import org.rivetengine.entity.Entity;
import org.rivetengine.entity.components.physics.collision.Collider;
import org.rivetengine.entity.components.physics.collision.Collider3d;
import org.rivetengine.entity.components.physics.collision.BoxCollider;
import org.rivetengine.physics.collision.BoundingBox;
import org.rivetengine.core.Scene;
import org.joml.Vector3f;
import org.joml.Vector2f;
import org.joml.Intersectionf;

import java.util.function.Predicate;

public class RayCast {
    private Scene scene;
    private Vector3f origin;
    private Vector3f direction;
    private float maxDistance = 1000f;
    private Predicate<Entity> filter;

    public RayCast(Scene scene) {
        this.scene = scene;
    }

    public RayCast(Scene scene, Vector3f origin, Vector3f direction) {
        this(scene, origin, direction, 1000);
    }

    public RayCast(Scene scene, Vector3f origin, Vector3f direction, float maxDistance) {
        this.scene = scene;
        this.origin = origin;
        this.direction = new Vector3f(direction).normalize();
        this.maxDistance = maxDistance;
    }

    public RayCast from(Vector3f origin) {
        this.origin = origin;
        return this;
    }

    public RayCast towards(Vector3f direction) {
        this.direction = new Vector3f(direction).normalize();
        return this;
    }

    public RayCast withMaxDistance(float maxDistance) {
        this.maxDistance = maxDistance;
        return this;
    }

    public RayCast withFilter(Predicate<Entity> filter) {
        this.filter = filter;
        return this;
    }

    public RayCastHit cast() {
        if (scene == null || origin == null || direction == null) {
            return null;
        }

        RayCastHit closestHit = null;
        float minDistance = maxDistance;

        for (Entity entity : scene.getAllEntities()) {
            if (filter != null && !filter.test(entity))
                continue;

            Collider3d collider = entity.getComponent(Collider3d.class);
            if (collider == null)
                continue;

            if (collider instanceof BoxCollider box) {
                BoundingBox boxBounds = box.getBox();
                Vector3f min = boxBounds.getMin();
                Vector3f max = boxBounds.getMax();

                Vector2f result = new Vector2f();
                if (Intersectionf.intersectRayAab(origin, direction, min, max, result)) {
                    float t = result.x;
                    if (t >= 0 && t < minDistance) {
                        minDistance = t;
                        Vector3f hitPoint = new Vector3f(origin).add(new Vector3f(direction).mul(t));
                        closestHit = new RayCastHit(hitPoint, collider, entity, t);
                    }
                }
            }
        }

        return closestHit;
    }

    public Vector3f getOrigin() {
        return origin;
    }

    public Vector3f getDirection() {
        return direction;
    }

    public float getMaxDistance() {
        return maxDistance;
    }
}
