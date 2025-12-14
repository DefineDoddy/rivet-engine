package org.rivetengine.entity.components.physics.body;

import org.joml.Vector3f;

public class KinematicBody extends PhysicsBody {
    public final Vector3f velocity = new Vector3f();
    public float gravityScale = 1f;
}
