package org.rivetengine.physics.collision;

import org.joml.Vector3f;

public class AABBCollision {

    public static boolean isColliding(BoundingBox a, BoundingBox b) {
        Vector3f aMin = a.getMin();
        Vector3f aMax = a.getMax();
        Vector3f bMin = b.getMin();
        Vector3f bMax = b.getMax();

        return aMin.x <= bMax.x && aMax.x >= bMin.x
                && aMin.y <= bMax.y && aMax.y >= bMin.y
                && aMin.z <= bMax.z && aMax.z >= bMin.z;
    }

    public static Vector3f getNormal(BoundingBox a, BoundingBox b) {
        Vector3f diff = new Vector3f(b.center).sub(a.center);
        Vector3f overlap = getOverlap(a, b);

        if (overlap.x <= 0 || overlap.y <= 0 || overlap.z <= 0) {
            return new Vector3f();
        }

        // Find axis of minimum penetration
        if (overlap.x <= overlap.y && overlap.x <= overlap.z) {
            return new Vector3f(Math.signum(diff.x), 0, 0);
        } else if (overlap.y <= overlap.x && overlap.y <= overlap.z) {
            return new Vector3f(0, Math.signum(diff.y), 0);
        } else {
            return new Vector3f(0, 0, Math.signum(diff.z));
        }
    }

    public static float getDepth(BoundingBox a, BoundingBox b) {
        Vector3f overlap = getOverlap(a, b);

        if (overlap.x <= 0 || overlap.y <= 0 || overlap.z <= 0) {
            return 0f;
        }

        return Math.min(overlap.x, Math.min(overlap.y, overlap.z));
    }

    private static Vector3f getOverlap(BoundingBox a, BoundingBox b) {
        Vector3f aMin = a.getMin();
        Vector3f aMax = a.getMax();
        Vector3f bMin = b.getMin();
        Vector3f bMax = b.getMax();

        return new Vector3f(
                Math.min(aMax.x, bMax.x) - Math.max(aMin.x, bMin.x),
                Math.min(aMax.y, bMax.y) - Math.max(aMin.y, bMin.y),
                Math.min(aMax.z, bMax.z) - Math.max(aMin.z, bMin.z));
    }
}