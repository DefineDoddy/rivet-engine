package org.rivetengine.entity.components.physics.body;

import org.rivetengine.entity.component.Component;
import org.rivetengine.entity.component.OptionallyRequires;
import org.rivetengine.entity.components.physics.collision.Collider;

@OptionallyRequires(Collider.class)
public abstract class PhysicsBody implements Component {
    public float mass = 1;
    public float restitution = 1f;

    public PhysicsBody() {
        this(1);
    }

    public PhysicsBody(float mass) {
        this.mass = mass;
        this.restitution = mass;
    }
}
