package me.definedoddy.engine.physics.collision;

import me.definedoddy.engine.debug.Debug;
import me.definedoddy.engine.debug.draw.Cube;
import me.definedoddy.engine.entity.ModelEntity;
import me.definedoddy.engine.physics.PhysicsContainer;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class BoxCollider extends Collider3d {
    private Vector3f scale;
    private BoundingBox box;

    private final List<Collider> colliding = new ArrayList<>();

    public BoxCollider() {
        this(new Vector3f(1, 1, 1));
    }

    public BoxCollider(Vector3f scale) {
        this.scale = scale;
    }

    @Override
    public void init() {
        super.init();

        if (getEntity() instanceof ModelEntity modelEntity) {
            box = modelEntity.getBoundingBox();
        }
    }

    @Override
    public void update() {
        for (Collider collider : PhysicsContainer.getColliders()) {
            if (collider.isColliding(this)) {
                colliding.add(collider);

                if (getEntity() instanceof Collidable collidable) {
                    collidable.onCollisionStay(collider);
                }
            } else {
                colliding.remove(collider);
            }
        }

        drawBoundingBox();
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
    public boolean isColliding(Collider collider) {
        if (collider == null || collider == this) return false;
        if (!isEnabled() || !collider.isEnabled()) return false;
        if (!(collider instanceof BoxCollider boxCol)) return false;

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
