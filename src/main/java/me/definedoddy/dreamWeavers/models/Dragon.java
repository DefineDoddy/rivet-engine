package me.definedoddy.dreamWeavers.models;

import me.definedoddy.engine.entity.ModelEntity;
import me.definedoddy.engine.input.Mouse;
import me.definedoddy.engine.physics.collision.BoxCollider;
import me.definedoddy.engine.physics.collision.Collidable;
import me.definedoddy.engine.physics.collision.raycast.RayCastHit;
import me.definedoddy.engine.physics.collision.raycast.RayCastUtils;
import me.definedoddy.engine.rendering.camera.Camera;
import me.definedoddy.engine.rendering.model.model.Model;
import me.definedoddy.engine.rendering.model.model.ModelRegistry;
import org.joml.Vector3f;

import java.awt.*;

public class Dragon extends ModelEntity implements Collidable {
    public Dragon() {
        this(new Vector3f());
    }

    public Dragon(Vector3f position) {
        super(position);
        addComponent(new BoxCollider());
        scale.set(0.5, 0.5, 0.5);
    }

    @Override
    public void update() {
        super.update();

        if (getComponent(BoxCollider.class) != null) {
            Vector3f point = RayCastUtils.screenToWorldPoint(Mouse.get().getX(), Mouse.get().getY());
            RayCastHit hit = RayCastUtils.rayCast(Camera.get().getPosition(), point);

            if (hit.isHit() && hit.getCollider().getEntity() == this) {
                scale.set(0.7, 0.7, 0.7);
            } else {
                scale.set(0.5, 0.5, 0.5);
            }
        }
    }

    @Override
    protected Model defineModel() {
        Model model = ModelRegistry.getModel("dragon");
        model.getMaterial().setShininess(1f);
        int randomColour = (int) (Math.random() * 0x1000000);
        model.getMaterial().setColour(new Color(randomColour));
        return model;
    }
}
