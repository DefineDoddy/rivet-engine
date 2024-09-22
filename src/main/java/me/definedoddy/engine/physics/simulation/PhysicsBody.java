package me.definedoddy.engine.physics.simulation;

import me.definedoddy.engine.entity.Component;
import me.definedoddy.engine.physics.PhysicsContainer;
import me.definedoddy.engine.physics.collision.BoxCollider;
import org.joml.Vector3f;

public abstract class PhysicsBody extends Component {
    public float mass = 1;
    public float restitution = 1f;

    protected BoxCollider collider;

    @Override
    public void init() {
        PhysicsContainer.addPhysicsBody(this);
    }

    @Override
    public void update() {
        if (collider == null) {
            collider = getEntity().getComponent(BoxCollider.class);
        }
    }

    @Override
    public void remove() {
        PhysicsContainer.removePhysicsBody(this);
    }

    protected Vector3f getPos() {
        return getEntity().getPosition();
    }

    public BoxCollider getCollider() {
        return collider;
    }
}
