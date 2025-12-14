package org.rivetengine.physics;

import org.joml.Vector3f;

public class Physics {
    public static final Vector3f GRAVITY = new Vector3f(0, -9.81f, 0);
    public static final float TIME_STEP = 1f / 60f;
    public static final float RAYCAST_STEP = 0.1f;
}
