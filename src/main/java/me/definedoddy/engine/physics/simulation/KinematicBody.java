package me.definedoddy.engine.physics.simulation;

import me.definedoddy.engine.physics.Physics;
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

    public void step(float deltaTime) {
        linearVelocity.add(0, Physics.GRAVITY * gravityMultiplier * deltaTime, 0);
    }

    public void simulate(float deltaTime) {
        getPos().add(new Vector3f(linearVelocity).mul(deltaTime));
    }

    public Vector3f getLinearVelocity() {
        return linearVelocity;
    }
}
