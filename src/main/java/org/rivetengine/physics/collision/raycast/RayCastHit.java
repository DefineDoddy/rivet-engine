package org.rivetengine.physics.collision.raycast;

import org.rivetengine.entity.Entity;
import org.rivetengine.entity.components.physics.collision.Collider;
import org.joml.Vector3f;

public class RayCastHit {
    public final Vector3f point;
    public final Collider collider;
    public final Entity entity;
    public final float distance;

    RayCastHit(Vector3f point, Collider collider, Entity entity, float distance) {
        this.point = point;
        this.collider = collider;
        this.entity = entity;
        this.distance = distance;
    }
}
