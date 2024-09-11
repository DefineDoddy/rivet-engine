package me.definedoddy.engine.physics;

import me.definedoddy.engine.physics.collision.AABBCollision;
import me.definedoddy.engine.physics.collision.Collider;
import me.definedoddy.engine.physics.simulation.PhysicsBody;
import me.definedoddy.engine.physics.simulation.Rigidbody;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class PhysicsContainer {
    private static final List<Collider> colliders = new ArrayList<>();
    private static final List<PhysicsBody> physicsBodies = new ArrayList<>();

    public static void addCollider(Collider collider) {
        colliders.add(collider);
    }

    public static void removeCollider(Collider collider) {
        colliders.remove(collider);
    }

    public static List<Collider> getColliders() {
        return colliders;
    }

    public static void addPhysicsBody(PhysicsBody physicsBody) {
        physicsBodies.add(physicsBody);
    }

    public static void removePhysicsBody(PhysicsBody physicsBody) {
        physicsBodies.remove(physicsBody);
    }

    public static List<PhysicsBody> getPhysicsBodies() {
        return physicsBodies;
    }

    public static void update() {
        for (PhysicsBody body : physicsBodies) {
            for (PhysicsBody other : physicsBodies) {
                if (body == other) continue;
                resolve(body, other);
            }
        }
    }

    private static void resolve(PhysicsBody aBody, PhysicsBody bBody) {
        if (aBody == null  || bBody == null) return;

        float aInvMass = aBody.mass != 0 ? 1f / aBody.mass : 1f;
        float bInvMass = bBody.mass != 0 ? 1f / bBody.mass : 1f;

        Vector3f normal = getNormal(aBody, bBody).div(2);
        float e = Math.min(aBody.restitution, bBody.restitution);

        Vector3f aLinearVelocity = (aBody instanceof Rigidbody rb) ? rb.getLinearVelocity() : new Vector3f();
        Vector3f bLinearVelocity = (bBody instanceof Rigidbody rb) ? rb.getLinearVelocity() : new Vector3f();

        // Impulse solver
        if (collidingWith(aBody, bBody)) {
            Vector3f relVelocity = new Vector3f(bLinearVelocity).sub(aLinearVelocity);
            float dot = relVelocity.dot(normal);
            if (dot > 0f) return;

            float j = (-1f + e) * dot / (aInvMass + bInvMass);

            Vector3f aImpulse = new Vector3f(normal).mul(j).mul(aInvMass).negate();
            Vector3f bImpulse = new Vector3f(normal).mul(j).mul(bInvMass);

            if (aBody instanceof Rigidbody rb) rb.addImpulseForce(aImpulse);
            if (bBody instanceof Rigidbody rb) rb.addImpulseForce(bImpulse);
        }

        // Position solver
        float depth = Math.min(getDepth(aBody, bBody) - 0.01f, 0f);
        Vector3f correction = new Vector3f(normal).mul(e).mul(depth).div(aInvMass);

        Vector3f aDelta = new Vector3f(correction).mul(aInvMass);
        Vector3f bDelta = new Vector3f(correction).mul(bInvMass);

        if (aBody instanceof Rigidbody rb) rb.addImpulseForce(aDelta);
        if (bBody instanceof Rigidbody rb) rb.addImpulseForce(bDelta);

        // Friction solver
        Vector3f tangent = new Vector3f(normal).cross(new Vector3f(0, 1, 0));
        Vector3f relVelocity = new Vector3f(aLinearVelocity).sub(bLinearVelocity);
        float dot = relVelocity.dot(tangent);

        Vector3f friction = new Vector3f(tangent).mul(dot).mul(0.5f);
        if (aBody instanceof Rigidbody rb) rb.addImpulseForce(friction);
        if (bBody instanceof Rigidbody rb) rb.addImpulseForce(friction.negate());
    }

    private static boolean collidingWith(PhysicsBody aBody, PhysicsBody bBody) {
        return aBody.getCollider() != null && aBody.getCollider().isColliding(bBody.getCollider());
    }

    private static Vector3f getNormal(PhysicsBody aBody, PhysicsBody bBody) {
        if (aBody.getCollider() == null || bBody.getCollider() == null) return new Vector3f();
        return AABBCollision.getNormal(aBody.getCollider().getBox(), bBody.getCollider().getBox());
    }

    private static float getDepth(PhysicsBody aBody, PhysicsBody bBody) {
        if (aBody.getCollider() == null || bBody.getCollider() == null) return 0;
        return AABBCollision.getDepth(aBody.getCollider().getBox(), bBody.getCollider().getBox());
    }
}
