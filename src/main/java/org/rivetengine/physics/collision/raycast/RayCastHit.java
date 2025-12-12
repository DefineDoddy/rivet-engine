package org.rivetengine.physics.collision.raycast;

import org.rivetengine.entity.components.physics.collision.Collider;
import org.joml.Vector3f;

public class RayCastHit {
    private final boolean hit;
    private final Vector3f point;
    private final Collider collider;
    private final float distance;

    RayCastHit(boolean hit, Vector3f point, Collider collider, float distance) {
        this.hit = hit;
        this.point = point;
        this.collider = collider;
        this.distance = distance;
    }

    public boolean isHit() {
        return hit;
    }

    public Vector3f getPoint() {
        return point;
    }

    public Collider getCollider() {
        return collider;
    }

    public float getDistance() {
        return distance;
    }
}
