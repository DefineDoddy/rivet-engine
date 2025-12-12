package org.rivetengine.entity.components.physics.collision;

import org.rivetengine.debug.Debug;
import org.rivetengine.debug.DebugHandler;
import org.rivetengine.debug.draw.Cube;
import org.rivetengine.physics.PhysicsContainer;
import org.rivetengine.physics.collision.AABBCollision;
import org.rivetengine.physics.collision.BoundingBox;
import org.rivetengine.physics.collision.Collidable;

import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class BoxCollider extends Collider3d {
    private final List<Collider> colliding = new ArrayList<>();

    @Override
    public void update() {
        if (!modelProvided) {
            box.set(new BoundingBox(new Vector3f(), scale));
            Vector3f halfScale = new Vector3f(scale).mul(0.5f);
            halfScale.y = 0;
            box.translate(new Vector3f(getEntity().getPosition()).sub(halfScale));
        }

        for (Collider collider : PhysicsContainer.getColliders()) {
            if (collider.isCollidingWith(this)) {
                colliding.add(collider);

                if (getEntity() instanceof Collidable collidable) {
                    collidable.onCollisionStay(collider);
                }
            } else {
                colliding.remove(collider);
            }
        }

        if (drawBoundingBox) {
            drawBoundingBox();
        }
    }

    private void drawBoundingBox() {
        Vector3f pos = box.getCenter();
        Vector3f size = box.getSize();
        Debug.drawCube(new Cube(pos, size));
    }

    @Override
    public void remove() {
        super.remove();
    }

    @Override
    public boolean isCollidingWith(Collider collider) {
        if (collider == null || collider == this)
            return false;
        if (!isEnabled() || !collider.isEnabled())
            return false;
        if (!(collider instanceof BoxCollider boxCol))
            return false;

        return AABBCollision.intersectBoundingBoxes(box, boxCol.getBox());
    }

    @Override
    public boolean containsPoint(Vector3f point) {
        return box.contains(point);
    }

    @Override
    public List<Collider> getColliding() {
        return colliding;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }

    public Vector3f getScale() {
        return scale;
    }

    public BoundingBox getBox() {
        return box;
    }
}
