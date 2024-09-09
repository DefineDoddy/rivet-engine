package me.definedoddy.engine.physics;

import me.definedoddy.engine.physics.collision.Collider;
import me.definedoddy.engine.physics.simulation.Rigidbody;

import java.util.ArrayList;
import java.util.List;

public class PhysicsContainer {
    private static final List<Collider> colliders = new ArrayList<>();
    private static final List<Rigidbody> rigidbodies = new ArrayList<>();

    public static void addCollider(Collider collider) {
        colliders.add(collider);
    }

    public static void removeCollider(Collider collider) {
        colliders.remove(collider);
    }

    public static List<Collider> getColliders() {
        return colliders;
    }

    public static void addRigidbody(Rigidbody rigidbody) {
        rigidbodies.add(rigidbody);
    }

    public static void removeRigidbody(Rigidbody rigidbody) {
        rigidbodies.remove(rigidbody);
    }

    public static List<Rigidbody> getRigidbodies() {
        return rigidbodies;
    }
}
