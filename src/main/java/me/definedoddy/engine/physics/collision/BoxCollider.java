package me.definedoddy.engine.physics.collision;

import me.definedoddy.engine.debug.Debug;
import me.definedoddy.engine.debug.DebugHandler;
import me.definedoddy.engine.debug.draw.Cube;
import me.definedoddy.engine.entity.ModelEntity;
import me.definedoddy.engine.physics.PhysicsContainer;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class BoxCollider extends Collider3d {
    private Vector3f scale;
    private BoundingBox box;

    private boolean modelProvided = false;
    private final List<Collider> colliding = new ArrayList<>();

    private final DebugHandler debugHandler = new DebugHandler();
    private boolean drawBoundingBox = false;

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
            modelProvided = true;
        } else {
            box = new BoundingBox(new Vector3f(), scale);
        }

        debugHandler.createWireframeCallback(v -> drawBoundingBox = v);
    }

    @Override
    public void update() {
        if (!modelProvided) {
            box.set(new BoundingBox(new Vector3f(), scale));
            Vector3f halfScale = new Vector3f(scale).mul(0.5f);
            halfScale.y = 0;
            box.translate(new Vector3f(getEntity().getPosition()).sub(halfScale));
        }

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
