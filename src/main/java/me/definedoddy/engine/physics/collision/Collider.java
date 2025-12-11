package me.definedoddy.engine.physics.collision;

import me.definedoddy.engine.entity.ComponentOld;
import me.definedoddy.engine.physics.PhysicsContainer;
import org.joml.Vector3f;

import java.util.List;

public abstract class Collider extends ComponentOld {
    public abstract boolean isColliding(Collider collider);

    public abstract boolean containsPoint(Vector3f point);

    public abstract List<Collider> getColliding();

    @Override
    public void init() {
        PhysicsContainer.addCollider(this);
    }

    @Override
    public void remove() {
        PhysicsContainer.removeCollider(this);
    }
}
