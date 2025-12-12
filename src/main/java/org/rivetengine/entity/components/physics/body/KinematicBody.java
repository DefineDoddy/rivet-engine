package org.rivetengine.entity.components.physics.body;

import org.joml.Vector3f;

public class KinematicBody extends PhysicsBody {
    public float gravityMultiplier = 1;

    protected final Vector3f linearVelocity = new Vector3f();

    public void move(Vector3f vector) {
        this.linearVelocity.add(vector);
    }

    public void setLinearVelocity(Vector3f velocity) {
        this.linearVelocity.set(velocity);
    }

    public Vector3f getLinearVelocity() {
        return linearVelocity;
    }
}
