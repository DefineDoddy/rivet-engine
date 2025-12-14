package org.rivetengine.entity.components.physics.body;

import org.joml.Vector3f;
import org.rivetengine.entity.component.Requires;
import org.rivetengine.entity.components.Transform;

@Requires(Transform.class)
public class Rigidbody extends PhysicsBody {
    public final Vector3f velocity = new Vector3f();
    public float gravityScale = 1f;

    public Rigidbody() {
        this(1f);
    }

    public Rigidbody(float mass) {
        super(mass);
    }
}
