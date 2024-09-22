package me.definedoddy.engine.physics;

import me.definedoddy.engine.context.Time;
import me.definedoddy.engine.physics.collision.AABBCollision;
import me.definedoddy.engine.physics.collision.Collider;
import me.definedoddy.engine.physics.simulation.KinematicBody;
import me.definedoddy.engine.physics.simulation.PhysicsBody;
import me.definedoddy.engine.physics.simulation.Rigidbody;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class PhysicsContainer {
    private static final List<Collider> colliders = new ArrayList<>();
    private static final List<PhysicsBody> physicsBodies = new ArrayList<>();

    private static final float timeStep = 1f / 60f;
    private static float accumulator = 0f;

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
        if (physicsBodies.size() < 2) return;

        float frameTime = (float) Time.getDeltaTime();
        accumulator += frameTime;

        while (accumulator >= timeStep) {
            integrate();
            resolve();
            simulate();
            accumulator -= timeStep;
        }
    }

    private static void integrate() {
        for (PhysicsBody body : physicsBodies) {
            if (body instanceof Rigidbody rb) rb.step(timeStep);
        }
    }

    private static void simulate() {
        for (PhysicsBody body : physicsBodies) {
            if (body instanceof Rigidbody rb) rb.simulate(timeStep);
        }
    }

    private static void resolve() {
        for (int a = 0; a < physicsBodies.size(); a++) {
            for (int b = a + 1; b < physicsBodies.size(); b++) {
                resolve(physicsBodies.get(a), physicsBodies.get(b));
            }
        }
    }

    private static void resolve(PhysicsBody aBody, PhysicsBody bBody) {
        if (aBody == null  || bBody == null) return;

        float aInvMass = aBody.mass != 0 ? 1f / aBody.mass : 1f;
        float bInvMass = bBody.mass != 0 ? 1f / bBody.mass : 1f;

        Vector3f normal = getNormal(aBody, bBody);
        float e = Math.min(aBody.restitution, bBody.restitution);

        Vector3f aLinearVelocity = (aBody instanceof Rigidbody rb) ? rb.getLinearVelocity() : new Vector3f();
        Vector3f bLinearVelocity = (bBody instanceof Rigidbody rb) ? rb.getLinearVelocity() : new Vector3f();

        float depth = getDepth(aBody, bBody);

        if (collidingWith(aBody, bBody)) {
            boolean aStatic = aBody instanceof KinematicBody;
            boolean bStatic = bBody instanceof KinematicBody;
            if (aStatic && bStatic) return;

            // Positional correction
            if (aStatic) {
                Vector3f bDelta = new Vector3f(normal).mul(depth);
                if (bBody instanceof Rigidbody rb) rb.move(bDelta);
            } else if (bStatic) {
                Vector3f aDelta = new Vector3f(normal).negate().mul(depth);
                if (aBody instanceof Rigidbody rb) rb.move(aDelta);
            } else {
                Vector3f delta = new Vector3f(normal).mul(depth / 2f);
                if (aBody instanceof Rigidbody rb) rb.move(new Vector3f(delta).negate());
                if (bBody instanceof Rigidbody rb) rb.move(delta);
            }

            // Impulse solver
            Vector3f relVelocity = new Vector3f(bLinearVelocity).sub(aLinearVelocity);
            float dot = relVelocity.dot(normal);
            if (dot > 0f) return;

            float j = -(1f + e) * dot;
            j /= aInvMass + bInvMass;

            Vector3f aImpulse = new Vector3f(normal).mul(j).mul(aInvMass).negate();
            Vector3f bImpulse = new Vector3f(normal).mul(j).mul(bInvMass);

            if (aBody instanceof Rigidbody rb) rb.move(aImpulse);
            if (bBody instanceof Rigidbody rb) rb.move(bImpulse);

            // Friction
            if (new Vector3f(relVelocity).absolute().lengthSquared() > 0) {
                Vector3f tangent = new Vector3f(relVelocity).sub(new Vector3f(normal).mul(dot)).normalize();
                if (Float.isNaN(tangent.x) || Float.isNaN(tangent.y) || Float.isNaN(tangent.z)) return;

                float jt = -relVelocity.dot(tangent);
                jt /= aInvMass + bInvMass;

                float friction = 0.2f;
                Vector3f aFriction = new Vector3f(tangent).mul(jt).mul(aInvMass).negate().mul(friction);
                Vector3f bFriction = new Vector3f(tangent).mul(jt).mul(bInvMass).mul(friction);

                if (aBody instanceof Rigidbody rb) rb.move(aFriction);
                if (bBody instanceof Rigidbody rb) rb.move(bFriction);
            }
        }
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
