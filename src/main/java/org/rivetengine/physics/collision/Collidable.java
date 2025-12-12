package org.rivetengine.physics.collision;

public interface Collidable {
    default void onCollisionEnter(Collider collider) {}
    default void onCollisionStay(Collider collider) {}
    default void onCollisionExit(Collider collider) {}
}
