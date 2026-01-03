package org.rivetengine.entity.components.physics.collision;

import org.rivetengine.entity.Entity;
import org.rivetengine.entity.components.Transform;
import org.rivetengine.physics.collision.BoundingBox;
import org.rivetengine.utils.EntityUtils;

import org.joml.Vector3f;

public class BoxCollider extends Collider3d {
    public final Vector3f size;
    public final Vector3f offset;

    private Entity entity;

    public BoxCollider() {
        this(new Vector3f(1, 1, 1), new Vector3f(0, 0, 0));
    }

    public BoxCollider(Vector3f size) {
        this(size, new Vector3f(0, 0, 0));
    }

    public BoxCollider(Vector3f size, Vector3f offset) {
        this.size = size;
        this.offset = offset;
    }

    public BoxCollider(BoundingBox bounds) {
        this(bounds != null ? bounds.halfExtents : new Vector3f(1),
                bounds != null ? bounds.center : new Vector3f(0));
    }

    public BoxCollider(Entity entity) {
        this(EntityUtils.getTotalBounds(entity));
    }

    public static BoxCollider fit(Entity entity) {
        return new BoxCollider(entity);
    }

    @Override
    public void onAttach(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void onDetach(Entity entity) {
        this.entity = null;
    }

    public BoundingBox getBox() {
        if (entity == null) {
            return new BoundingBox(new Vector3f(offset), size);
        }

        Transform transform = entity.getComponent(Transform.class);
        if (transform == null) {
            return new BoundingBox(new Vector3f(offset), size);
        }

        Vector3f center = new Vector3f(transform.position).add(offset);
        return new BoundingBox(center, size);
    }
}
