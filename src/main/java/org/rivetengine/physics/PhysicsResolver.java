package org.rivetengine.physics;

import org.rivetengine.entity.Entity;
import org.rivetengine.entity.components.Transform;
import org.rivetengine.entity.components.physics.body.PhysicsBody;
import org.rivetengine.entity.components.physics.body.Rigidbody;
import org.rivetengine.entity.components.physics.collision.BoxCollider;
import org.rivetengine.entity.components.physics.collision.Collider3d;
import org.rivetengine.physics.collision.AABBCollision;
import org.rivetengine.system.SystemUtils;

import org.joml.Vector3f;

import java.util.List;

public class PhysicsResolver {

    public void resolve(List<Entity> entities) {
        for (int i = 0; i < entities.size(); i++) {
            for (int j = i + 1; j < entities.size(); j++) {
                resolve(entities.get(i), entities.get(j));
            }
        }
    }

    private void resolve(Entity aEntity, Entity bEntity) {
        Collider3d aCol = aEntity.getComponent(Collider3d.class);
        Collider3d bCol = bEntity.getComponent(Collider3d.class);

        if (aCol == null || bCol == null) {
            return;
        }
        if (!isCollidingWith(aCol, bCol)) {
            return;
        }

        PhysicsBody aBody = aEntity.getComponent(PhysicsBody.class);
        PhysicsBody bBody = bEntity.getComponent(PhysicsBody.class);

        float aInvMass = getInvMass(aBody);
        float bInvMass = getInvMass(bBody);

        // Both static, do nothing
        if (aInvMass == 0f && bInvMass == 0f) {
            return;
        }

        Vector3f normal = getNormal(aCol, bCol);
        float depth = getDepth(aCol, bCol);

        correctPositions(aEntity, bEntity, aInvMass, bInvMass, normal, depth);
        applyImpulse(aBody, bBody, aInvMass, bInvMass, normal);
    }

    private void correctPositions(Entity aEntity, Entity bEntity, float aInvMass, float bInvMass, Vector3f normal,
            float depth) {
        float totalInvMass = aInvMass + bInvMass;
        if (totalInvMass == 0f) {
            return;
        }

        Vector3f correction = new Vector3f(normal).mul(depth / totalInvMass);

        Transform aTransform = SystemUtils.getTransformSafe(aEntity);
        Transform bTransform = SystemUtils.getTransformSafe(bEntity);

        aTransform.position.fma(-aInvMass, correction);
        bTransform.position.fma(bInvMass, correction);
    }

    private void applyImpulse(PhysicsBody aBody, PhysicsBody bBody, float aInvMass, float bInvMass, Vector3f normal) {
        Vector3f aVel = getVelocity(aBody);
        Vector3f bVel = getVelocity(bBody);

        Vector3f relVel = new Vector3f(bVel).sub(aVel);
        float velAlongNormal = relVel.dot(normal);

        // Separating, no impulse needed
        if (velAlongNormal > 0f) {
            return;
        }

        float e = Math.min(getRestitution(aBody), getRestitution(bBody));
        float j = -(1f + e) * velAlongNormal / (aInvMass + bInvMass);

        Vector3f impulse = new Vector3f(normal).mul(j);

        if (aBody instanceof Rigidbody rb) {
            rb.velocity.fma(-aInvMass, impulse);
        }
        if (bBody instanceof Rigidbody rb) {
            rb.velocity.fma(bInvMass, impulse);
        }

        applyFriction(aBody, bBody, aInvMass, bInvMass, normal, relVel, j);
    }

    private void applyFriction(PhysicsBody aBody, PhysicsBody bBody, float aInvMass, float bInvMass, Vector3f normal,
            Vector3f relVel, float normalImpulse) {
        // Recalculate relative velocity after impulse
        Vector3f aVel = getVelocity(aBody);
        Vector3f bVel = getVelocity(bBody);
        relVel = new Vector3f(bVel).sub(aVel);

        Vector3f tangent = new Vector3f(relVel).sub(new Vector3f(normal).mul(relVel.dot(normal)));
        if (tangent.lengthSquared() < 1e-6f) {
            return;
        }

        tangent.normalize();

        float jt = -relVel.dot(tangent) / (aInvMass + bInvMass);

        // Coulomb friction clamp
        float friction = 0.3f;
        float maxFriction = friction * Math.abs(normalImpulse);
        jt = Math.max(-maxFriction, Math.min(maxFriction, jt));

        Vector3f frictionImpulse = new Vector3f(tangent).mul(jt);

        if (aBody instanceof Rigidbody rb) {
            rb.velocity.fma(-aInvMass, frictionImpulse);
        }
        if (bBody instanceof Rigidbody rb) {
            rb.velocity.fma(bInvMass, frictionImpulse);
        }
    }

    private float getInvMass(PhysicsBody body) {
        if (body == null) {
            return 0f; // No body = static
        }
        if (body instanceof Rigidbody rb) {
            return rb.mass > 0f ? 1f / rb.mass : 0f;
        }
        return 0f; // Kinematic/Static = immovable
    }

    private Vector3f getVelocity(PhysicsBody body) {
        if (body instanceof Rigidbody rb) {
            return rb.velocity;
        }
        return new Vector3f();
    }

    private float getRestitution(PhysicsBody body) {
        if (body != null) {
            return body.restitution;
        }
        return 0f;
    }

    private Vector3f getNormal(Collider3d a, Collider3d b) {
        if (a instanceof BoxCollider aBox && b instanceof BoxCollider bBox) {
            return AABBCollision.getNormal(aBox.getBox(), bBox.getBox());
        }
        // if (a instanceof SphereCollider && b instanceof SphereCollider) {
        // return sphereNormal((SphereCollider) a, (SphereCollider) b);
        // }
        // if (a instanceof BoxCollider && b instanceof SphereCollider) {
        // return boxSphereNormal((BoxCollider) a, (SphereCollider) b);
        // }
        // if (a instanceof SphereCollider && b instanceof BoxCollider) {
        // return boxSphereNormal((BoxCollider) b, (SphereCollider) a).negate();
        // }
        return new Vector3f();
    }

    private float getDepth(Collider3d a, Collider3d b) {
        if (a instanceof BoxCollider aBox && b instanceof BoxCollider bBox) {
            return AABBCollision.getDepth(aBox.getBox(), bBox.getBox());
        }
        // ... other cases
        return 0f;
    }

    private boolean isCollidingWith(Collider3d a, Collider3d b) {
        if (a instanceof BoxCollider && b instanceof BoxCollider) {
            return boxVsBox((BoxCollider) a, (BoxCollider) b);
        }
        // if (a instanceof SphereCollider && b instanceof SphereCollider) {
        // return sphereVsSphere((SphereCollider) a, (SphereCollider) b);
        // }
        // if (a instanceof BoxCollider && b instanceof SphereCollider) {
        // return boxVsSphere((BoxCollider) a, (SphereCollider) b);
        // }
        // if (a instanceof SphereCollider && b instanceof BoxCollider) {
        // return boxVsSphere((BoxCollider) b, (SphereCollider) a);
        // }
        return false;
    }

    private boolean boxVsBox(BoxCollider a, BoxCollider b) {
        return AABBCollision.isColliding(a.getBox(), b.getBox());
    }
}