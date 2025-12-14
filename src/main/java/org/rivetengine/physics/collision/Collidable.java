package org.rivetengine.physics.collision;

import org.rivetengine.entity.components.physics.collision.Collider;

public interface Collidable {
    default void onCollisionEnter(Collider collider) {
    }

    default void onCollisionStay(Collider collider) {
    }

    default void onCollisionExit(Collider collider) {
    }
}
