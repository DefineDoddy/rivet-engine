package org.rivetengine.physics.collision.raycast;

import org.rivetengine.entity.components.physics.collision.Collider;
import org.joml.Vector3f;

public class RayCastHit {
    public final boolean isHit;
    public final Vector3f point;
    public final Collider collider;
    public final float distance;

    RayCastHit(boolean isHit, Vector3f point, Collider collider, float distance) {
        this.isHit = isHit;
        this.point = point;
        this.collider = collider;
        this.distance = distance;
    }
}
