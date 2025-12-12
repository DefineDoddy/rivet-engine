package org.rivetengine.entity.components.physics.collision;

import org.rivetengine.entity.component.Component;
import org.joml.Vector3f;

import java.util.List;

public abstract class Collider implements Component {
    public abstract boolean isCollidingWith(Collider collider);

    public abstract boolean containsPoint(Vector3f point);

    public abstract List<Collider> getColliding();
}
