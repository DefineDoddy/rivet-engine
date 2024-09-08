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

    private Vector3f box;
    private Vector3f origin;
    private Vector3f bounds;

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
            box = new Vector3f(modelEntity.getModel().getBoundingBox()).div(2);
            box.max(new Vector3f(0.1f));
            origin = modelEntity.getModel().getOrigin();
            updateBounds();
        }
    }

    @Override
    public void update() {
        updateBounds();

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
        Vector3f pos = getPosition();
        Vector3f size = new Vector3f(bounds).mul(2);
        Debug.drawCube(new Cube(pos, size));
    }

    private void updateBounds() {
        Vector3f entityScale = getEntity().getScale();
        bounds = new Vector3f(box).mul(entityScale).mul(scale);
    }

    private Vector3f getPosition() {
        Vector3f entityPos = getEntity().getPosition();
        Vector3f entityScale = getEntity().getScale();
        return new Vector3f(entityPos).add(new Vector3f(origin).mul(entityScale));
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

        Vector3f pos = getPosition();
        Vector3f otherPos = boxCol.getPosition();

        Vector3f min = new Vector3f(pos).sub(bounds);
        Vector3f max = new Vector3f(pos).add(bounds);
        Vector3f otherMin = new Vector3f(otherPos).sub(boxCol.bounds);
        Vector3f otherMax = new Vector3f(otherPos).add(boxCol.bounds);

        return min.x < otherMax.x && max.x > otherMin.x &&
                min.y < otherMax.y && max.y > otherMin.y &&
                min.z < otherMax.z && max.z > otherMin.z;
    }

    @Override
    public boolean containsPoint(Vector3f point) {
        Vector3f pos = getPosition();
        Vector3f min = new Vector3f(pos).sub(bounds);
        Vector3f max = new Vector3f(pos).add(bounds);

        return point.x > min.x && point.x < max.x &&
                point.y > min.y && point.y < max.y &&
                point.z > min.z && point.z < max.z;
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

    public void setBox(Vector3f box) {
        this.box = box;
    }

    public Vector3f getBox() {
        return box;
    }
}
