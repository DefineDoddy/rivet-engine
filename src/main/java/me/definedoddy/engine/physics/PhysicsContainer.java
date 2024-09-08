package me.definedoddy.engine.physics;

import me.definedoddy.engine.physics.collision.Collider;

import java.util.ArrayList;
import java.util.List;

public class PhysicsContainer {
    private static final List<Collider> colliders = new ArrayList<>();

    public static void addCollider(Collider collider) {
        colliders.add(collider);
    }

    public static void removeCollider(Collider collider) {
        colliders.remove(collider);
    }

    public static List<Collider> getColliders() {
        return colliders;
    }
}
