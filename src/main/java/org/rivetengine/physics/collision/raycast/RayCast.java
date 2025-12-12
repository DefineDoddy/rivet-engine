package org.rivetengine.physics.collision.raycast;

import org.rivetengine.physics.Physics;
import org.rivetengine.entity.components.physics.collision.Collider;
import org.joml.Vector3f;

public class RayCast {
    private final Vector3f origin;
    private final Vector3f direction;
    private final float maxDistance;

    public RayCast(Vector3f origin, Vector3f direction) {
        this(origin, direction, 1000);
    }

    public RayCast(Vector3f origin, Vector3f direction, float maxDistance) {
        this.origin = origin;
        this.direction = direction;
        this.maxDistance = maxDistance;
    }

    public RayCastHit cast() {
        Vector3f point = new Vector3f(origin);

        for (float i = 0; i < maxDistance; i += Physics.RAYCAST_STEP) {
            point.add(direction);

            Collider collider = RayCastUtils.getIntersectingCollider(point);
            if (collider != null) return new RayCastHit(true, point, collider, i);
        }

        return new RayCastHit(false, point, null, maxDistance);
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
