package org.rivetengine.entity.components.physics.body;

import org.joml.Vector3f;

public class Rigidbody extends PhysicsBody {
    public float gravityMultiplier = 1f;

    protected final Vector3f linearVelocity = new Vector3f();

    public Rigidbody() {
        this(1f);
    }

    public Rigidbody(float mass) {
        super(mass);
    }

    public void move(Vector3f vector) {
        this.linearVelocity.add(vector);
    }

    public void setLinearVelocity(Vector3f velocity) {
        this.linearVelocity.set(velocity);
    }

    public Vector3f getLinearVelocity() {
        return linearVelocity;
    }

    public void addForce(Vector3f force) {
        linearVelocity.add(force);
    }
}
