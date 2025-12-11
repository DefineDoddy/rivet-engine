package me.definedoddy.dreamWeavers.player;

import me.definedoddy.engine.context.Time;
import me.definedoddy.engine.entity.Entity;
import me.definedoddy.engine.input.KeyCode;
import me.definedoddy.engine.input.Keyboard;
import me.definedoddy.engine.physics.collision.BoundingBox;
import me.definedoddy.engine.physics.collision.BoxCollider;
import me.definedoddy.engine.physics.simulation.KinematicBody;
import me.definedoddy.engine.rendering.camera.Camera;
import org.joml.Vector3f;

public class Player extends Entity {
    private final Camera camera;

    private final float movementSpeed = 0.05f;
    private final float acceleration = 0.15f;

    public Player() {
        camera = addChild(new FPSCamera());
        addComponent(new KinematicBody());
        addComponent(new BoxCollider(BoundingBox.fromCenter(new Vector3f(), new Vector3f(0.5f, 1.5f, 0.5f))));
    }

    @Override
    public void init() {
        super.init();

        position.set(new Vector3f(0f, 3f, -3f));
        rotation.set(new Vector3f(0, 90, 0));
    }

    @Override
    public void update() {
        super.update();

        calcMovement();
        updateCamera();
    }

    private void travel(float forward, float strafe, float up) {
        if (forward == 0 && strafe == 0 && up == 0) return;

        float yawRad = (float) Math.toRadians(camera.getYaw());
        float x = (float) (strafe * Math.cos(yawRad) + forward * Math.sin(yawRad)) * (float) Time.getDeltaTime();
        float z = (float) (strafe * Math.sin(yawRad) - forward * Math.cos(yawRad)) * (float) Time.getDeltaTime();

        Vector3f vector = new Vector3f(x, up, z);
        vector.normalize().mul(movementSpeed);
        getComponent(KinematicBody.class).move(vector);
    }

    private void calcMovement() {
        float forward = 0f, strafe = 0f, up = 0f;
        if (Keyboard.get().isKeyPressed(KeyCode.W)) forward += 1f;
        if (Keyboard.get().isKeyPressed(KeyCode.S)) forward -= 1f;
        if (Keyboard.get().isKeyPressed(KeyCode.A)) strafe -= 1f;
        if (Keyboard.get().isKeyPressed(KeyCode.D)) strafe += 1f;
        if (Keyboard.get().isKeyPressed(KeyCode.SPACE)) up += 1f;
        if (Keyboard.get().isKeyPressed(KeyCode.SHIFT)) up -= 1f;

        travel(forward, strafe, up);
    }

    private void updateCamera() {
        camera.setPosition(position);
        rotation.set(camera.getRotation());
    }
}
