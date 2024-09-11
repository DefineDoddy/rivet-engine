package me.definedoddy.engine.physics.simulation;

import me.definedoddy.engine.context.Time;
import me.definedoddy.engine.physics.Physics;
import org.joml.Vector3f;

public class Rigidbody extends PhysicsBody {
    public float gravityMultiplier = 1;

    protected final Vector3f linearVelocity = new Vector3f();
    protected final Vector3f angularVelocity = new Vector3f();
    protected final Vector3f force = new Vector3f();
    protected final Vector3f torque = new Vector3f();

    protected float inertia;

    @Override
    public void init() {
        super.init();
        calculateInertia();
    }

    @Override
    public void update() {
        float deltaTime = (float) Time.getFixedDeltaTime();
        simulate(deltaTime);

        super.update();

        addPos(new Vector3f(linearVelocity).mul(deltaTime));
    }

    public void addImpulseForce(Vector3f force) {
        this.linearVelocity.add(force);
    }

    private void calculateInertia() {
        if (collider == null) return;

        Vector3f size = collider.getBox().getSize();
        inertia = (1f / 12f) * mass * (size.x * size.x + size.y * size.y + size.z * size.z);
    }

    private void computeForceAndTorque() {
        if (collider == null) return;

        Vector3f force = new Vector3f(0, Physics.GRAVITY * gravityMultiplier, 0);
        this.force.add(force);

        Vector3f box = collider.getBox().getSize();
        Vector3f r = new Vector3f(box.x / 2, box.y / 2, box.z / 2);
        this.torque.set(r.y * force.z - r.z * force.y, r.z * force.x - r.x * force.z, r.x * force.y - r.y * force.x);
    }

    private void simulate(float deltaTime) {
        computeForceAndTorque();

        Vector3f linearAcceleration = new Vector3f(force).div(mass);
        linearVelocity.add(new Vector3f(linearAcceleration).mul(deltaTime));

        Vector3f angularAcceleration = new Vector3f(torque).div(inertia);
        angularVelocity.add(new Vector3f(angularAcceleration).mul(deltaTime));
        addRot(new Vector3f(angularVelocity).mul(deltaTime));

        force.set(0);
    }

    public Vector3f getLinearVelocity() {
        return linearVelocity;
    }
}
