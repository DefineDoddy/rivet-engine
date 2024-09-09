package me.definedoddy.engine.physics.collision;

import org.joml.Vector3f;

public class AABBCollision {
    public static boolean intersectBoundingBoxes(BoundingBox a, BoundingBox b) {
        return a.getMin().x < b.getMax().x && a.getMax().x > b.getMin().x &&
                a.getMin().y < b.getMax().y && a.getMax().y > b.getMin().y &&
                a.getMin().z < b.getMax().z && a.getMax().z > b.getMin().z;
    }

    public static Vector3f getCorrection(BoundingBox a, BoundingBox b) {
        /*// calc overlap in x, y, z
        float dx = Math.min(a.getMax().x, b.getMax().x) - Math.max(a.getMin().x, b.getMin().x);
        float dy = Math.min(a.getMax().y, b.getMax().y) - Math.max(a.getMin().y, b.getMin().y);
        float dz = Math.min(a.getMax().z, b.getMax().z) - Math.max(a.getMin().z, b.getMin().z);

        // calc penetration depth
        float penetration = Math.min(Math.min(dx, dy), dz);

        // calc direction of penetration
        float signX = Math.signum(b.getCenter().x - a.getCenter().x);
        float signY = Math.signum(b.getCenter().y - a.getCenter().y);
        float signZ = Math.signum(b.getCenter().z - a.getCenter().z);

        return new Vector3f(signX * penetration, signY * penetration, signZ * penetration);*/

        float dx = b.getCenter().x - a.getCenter().x;
        float px = (b.getSize().x + a.getSize().x) - Math.abs(dx);
        if (px <= 0) return new Vector3f();

        float dy = b.getCenter().y - a.getCenter().y;
        float py = (b.getSize().y + a.getSize().y) - Math.abs(dy);
        if (py <= 0) return new Vector3f();

        float dz = b.getCenter().z - a.getCenter().z;
        float pz = (b.getSize().z + a.getSize().z) - Math.abs(dz);
        if (pz <= 0) return new Vector3f();

        if (px < py && px < pz) {
            return new Vector3f(px * Math.signum(dx), 0, 0);
        } else if (py < pz) {
            return new Vector3f(0, py * Math.signum(dy), 0);
        } else {
            return new Vector3f(0, 0, pz * Math.signum(dz));
        }
    }
}
