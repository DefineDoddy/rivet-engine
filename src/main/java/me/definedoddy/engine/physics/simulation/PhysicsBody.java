package me.definedoddy.engine.physics.simulation;

import me.definedoddy.engine.entity.Component;
import me.definedoddy.engine.physics.collision.AABBCollision;
import me.definedoddy.engine.physics.collision.BoxCollider;
import me.definedoddy.engine.physics.collision.Collider;
import org.joml.Vector3f;

public abstract class PhysicsBody extends Component {
    public float mass = 1;
    public float restitution = 0.8f;

    protected BoxCollider collider;

    @Override
    public void update() {
        if (collider == null) {
            collider = getEntity().getComponent(BoxCollider.class);
        }

        handleCollisions();
    }

    protected void handleCollisions() {
        for (Collider collider : collider.getColliding()) {
            PhysicsBody body = collider.getEntity().getComponent(PhysicsBody.class);

            switch (this) {
                case Rigidbody rb when body instanceof Rigidbody rb2 -> resolveCollisionRBRB(rb, rb2);
                case Rigidbody rb when body instanceof KinematicBody kb -> resolveCollisionRBKB(rb, kb);
                //case KinematicBody kb when body instanceof Rigidbody rb -> resolveCollisionKBRB(kb, rb);
                default -> {}
            }
        }
    }

    private void resolveCollisionKBRB(KinematicBody thisBody, Rigidbody otherBody) {
        if (thisBody == null  || otherBody == null) return;

        Vector3f correction = getCorrection(otherBody);

        Vector3f separation = new Vector3f(correction);
        otherBody.addPos(separation);

        Vector3f relVelocity = new Vector3f(otherBody.linearVelocity);
        if (relVelocity.dot(correction) > 0f) return;

        float e = Math.min(thisBody.restitution, otherBody.restitution);
        float j = (-1f + e) * relVelocity.dot(correction);
        j /= 1f / otherBody.mass;

        Vector3f impulse = correction.mul(j / otherBody.mass);
        otherBody.linearVelocity.sub(impulse);
    }

    private void resolveCollisionRBKB(Rigidbody thisBody, KinematicBody otherBody) {
        if (thisBody == null  || otherBody == null) return;

        Vector3f correction = getCorrection(otherBody);

        Vector3f separation = new Vector3f(correction).negate();
        thisBody.linearVelocity.set(separation);

        Vector3f relVelocity = new Vector3f(thisBody.linearVelocity);
        if (relVelocity.dot(correction) > 0f) return;

        float e = Math.min(thisBody.restitution, otherBody.restitution);
        float j = (-1f + e) * relVelocity.dot(correction);
        j /= 1f / thisBody.mass;

        Vector3f impulse = correction.mul(j / thisBody.mass);
        thisBody.linearVelocity.sub(impulse);
    }

    private void resolveCollisionRBRB(Rigidbody thisBody, Rigidbody otherBody) {
        if (thisBody == null  || otherBody == null) return;

        Vector3f correction = getCorrection(otherBody);

        /*Vector3f thisSep = new Vector3f(correction).negate();
        thisBody.linearVelocity.set(thisSep);

        Vector3f otherSep = new Vector3f(correction);
        otherBody.linearVelocity.set(otherSep);*/

        Vector3f relVelocity = new Vector3f(otherBody.linearVelocity).sub(thisBody.linearVelocity);
        float e = Math.min(thisBody.restitution, otherBody.restitution);
        float j = (-1f + e) * relVelocity.dot(correction);
        j /= 1f / thisBody.mass + 1f / otherBody.mass;

        Vector3f impulse = correction.mul(j / thisBody.mass);
        thisBody.linearVelocity.sub(impulse);
        otherBody.linearVelocity.add(impulse);
    }

    private Vector3f getCorrection(PhysicsBody body) {
        if (collider == null ||body.collider == null) return new Vector3f();
        return AABBCollision.getCorrection(collider.getBox(), body.collider.getBox());
    }

    protected Vector3f getPos() {
        return getEntity().getPosition();
    }

    protected void setPos(Vector3f pos) {
        getEntity().getPosition().set(pos);
    }

    protected void addPos(Vector3f pos) {
        getEntity().getPosition().add(pos);
    }

    protected Vector3f getRot() {
        return getEntity().getRotation();
    }

    protected void setRot(Vector3f rot) {
        getEntity().getRotation().set(rot);
    }

    protected void addRot(Vector3f rot) {
        //getEntity().getRotation().add(rot);
    }
}
