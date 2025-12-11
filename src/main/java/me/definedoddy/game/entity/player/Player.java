package me.definedoddy.game.entity.player;

import me.definedoddy.engine.entity.Entity;
import me.definedoddy.engine.physics.collision.BoxCollider;
import me.definedoddy.engine.physics.simulation.Rigidbody;
import me.definedoddy.game.entity.player.camera.FPCamera;
import org.joml.Vector3f;

public class Player extends Entity {
    private final FPCamera camera;
    private final PlayerController controller;

    private final Vector3f velocity = new Vector3f();

    public Player(Vector3f position) {
        super(position);

        camera = new FPCamera(new Vector3f(position));
        addChild(camera);

        controller = new PlayerController(this);

        addComponent(new BoxCollider(new Vector3f(1f, 3.8f, 1f)));
        Rigidbody rb = addComponent(new Rigidbody(0.4f));
        rb.setGravityMultiplier(1.5f);
    }

    @Override
    public void update() {
        super.update();
        camera.update();
        controller.update();
    }

    public Vector3f getVelocity() {
        return velocity;
    }

    public FPCamera getCamera() {
        return camera;
    }
}
