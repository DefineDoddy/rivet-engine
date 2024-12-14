package me.definedoddy.dreamWeavers.models;

import me.definedoddy.engine.entity.ModelEntity;
import me.definedoddy.engine.input.Mouse;
import me.definedoddy.engine.input.MouseBtn;
import me.definedoddy.engine.physics.collision.BoxCollider;
import me.definedoddy.engine.physics.collision.Collidable;
import me.definedoddy.engine.physics.collision.raycast.RayCastHit;
import me.definedoddy.engine.physics.collision.raycast.RayCastUtils;
import me.definedoddy.engine.physics.simulation.KinematicBody;
import me.definedoddy.engine.rendering.camera.Camera;
import me.definedoddy.engine.rendering.lighting.PointLight;
import me.definedoddy.engine.rendering.model.model.Model;
import me.definedoddy.engine.rendering.model.model.ModelRegistry;
import me.definedoddy.engine.scene.SceneManager;
import org.joml.Vector3f;

import java.awt.*;

public class Stall extends ModelEntity implements Collidable {
    private final Vector3f mouseOffset = new Vector3f();
    private boolean isDragging = false;
    private boolean hovering = false;

    private final PointLight light;

    public Stall() {
        this(new Vector3f());
    }

    public Stall(Vector3f position) {
        super(position);
        addComponent(new BoxCollider());
        addComponent(new KinematicBody());

        light = new PointLight(new Vector3f(getPosition()).add(0, 3.5f, 1f), Color.ORANGE);
        light.setRadius(80);
        SceneManager.getCurrent().addLight(light);
    }

    @Override
    public void update() {
        super.update();
        light.setPosition(new Vector3f(getPosition()).add(0, 3.5f, 1f));

        if (getComponent(BoxCollider.class) != null && !isDragging) {
            Vector3f point = RayCastUtils.screenToWorldPoint(Mouse.get().getX(), Mouse.get().getY());
            RayCastHit hit = RayCastUtils.rayCast(Camera.get().getPosition(), point);

            if (hit.isHit() && hit.getCollider().getEntity() == this) {
                scale.set(1.2, 1.2, 1.2);
                hovering = true;
            } else {
                scale.set(1, 1, 1);
                hovering = false;
            }
        }

        getComponent(BoxCollider.class).setEnabled(!isDragging);

        if (Mouse.get().isButtonPressed(MouseBtn.LEFT)) {
            if (hovering) {
                Vector3f point = RayCastUtils.screenToWorldPoint(Mouse.get().getX(), Mouse.get().getY());
                RayCastHit hit = RayCastUtils.rayCast(Camera.get().getPosition(), point);

                if (!isDragging) {
                    mouseOffset.set(getPosition()).sub(hit.getPoint());
                    mouseOffset.y = 0;
                    isDragging = true;
                }

                Vector3f newPos = new Vector3f(hit.getPoint()).add(mouseOffset);
                position.set(newPos);
            }
        } else {
            isDragging = false;
        }
    }

    @Override
    protected Model defineModel() {
        return ModelRegistry.getModel("stall");
    }
}
