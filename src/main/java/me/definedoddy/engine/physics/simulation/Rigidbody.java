package me.definedoddy.engine.physics.simulation;

import me.definedoddy.engine.context.Time;
import me.definedoddy.engine.entity.Component;
import me.definedoddy.engine.physics.Physics;
import me.definedoddy.engine.physics.collision.Collider;
import org.joml.Vector3f;

import java.util.List;

public class Rigidbody extends Component {
    public float mass = 1;

    private final Vector3f velocity = new Vector3f();
    private final Vector3f acceleration = new Vector3f();
    private final Vector3f force = new Vector3f();

    private final Vector3f angularVelocity = new Vector3f();
    private final Vector3f angularAcceleration = new Vector3f();
    private final Vector3f torque = new Vector3f();

    @Override
    public void init() {

    }

    @Override
    public void update() {
        float deltaTime = (float) Time.getDeltaTime();

        applyGravity();
        updateRigidbody(deltaTime);
        handleCollision();

        getEntity().getPosition().add(new Vector3f(velocity).mul(deltaTime));
        getEntity().getRotation().add(new Vector3f(angularVelocity).mul(deltaTime));
    }

    public void applyForce(Vector3f force) {
        applyForce(force, new Vector3f());
    }

    public void applyForce(Vector3f force, Vector3f point) {
        this.force.add(force);
        torque.add(new Vector3f().cross(point, force));
    }

    private void updateRigidbody(float deltaTime) {
        acceleration.set(force).div(mass);
        velocity.add(acceleration.mul(deltaTime));
        force.zero();

        angularAcceleration.set(torque).div(mass);
        angularVelocity.add(angularAcceleration.mul(deltaTime));
        torque.zero();
    }

    private void applyGravity() {
        Vector3f force = new Vector3f(0, Physics.GRAVITY * mass, 0);
        applyForce(force);
    }

    private void handleCollision() {
        Collider collider = getEntity().getComponent(Collider.class);
        if (collider != null) {
            List<Collider> colliding = collider.getColliding();
            for (Collider other : colliding) {
                if (other.getEntity().getComponent(Rigidbody.class) != null) {
                    Rigidbody otherRigidbody = other.getEntity().getComponent(Rigidbody.class);
                    Vector3f normal = new Vector3f(other.getEntity().getPosition()).sub(getEntity().getPosition()).normalize();
                    float relativeVelocity = new Vector3f(velocity).sub(otherRigidbody.velocity).dot(normal);
                    if (relativeVelocity > 0) {
                        return;
                    }
                    float e = 0.5f;
                    float j = -(1 + e) * relativeVelocity;
                    j /= 1 / mass + 1 / otherRigidbody.mass;
                    Vector3f impulse = new Vector3f(normal).mul(j);
                    applyForce(impulse);
                    otherRigidbody.applyForce(new Vector3f(impulse).negate());
                } else {
                    Vector3f normal = new Vector3f(other.getEntity().getPosition()).sub(getEntity().getPosition()).normalize();
                    Vector3f point = new Vector3f(other.getEntity().getPosition());
                    applyForce(new Vector3f(normal).mul(-1), point);
                }
            }
        }
    }

    @Override
    public void remove() {

    }
}
