package org.rivetengine.physics.collision;

import org.joml.Vector3f;

public class BoundingBox {
    public final Vector3f center;
    public final Vector3f halfExtents;

    public BoundingBox(Vector3f center, Vector3f halfExtents) {
        this.center = center;
        this.halfExtents = halfExtents;
    }

    public Vector3f getMin() {
        return new Vector3f(center).sub(halfExtents);
    }

    public Vector3f getMax() {
        return new Vector3f(center).add(halfExtents);
    }

    public static BoundingBox fromMinMax(Vector3f min, Vector3f max) {
        Vector3f center = new Vector3f(min).add(max).mul(0.5f);
        Vector3f halfExtents = new Vector3f(max).sub(min).mul(0.5f);
        return new BoundingBox(center, halfExtents);
    }
}