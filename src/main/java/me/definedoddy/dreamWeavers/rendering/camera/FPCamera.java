package me.definedoddy.dreamWeavers.rendering.camera;

import me.definedoddy.engine.input.KeyCode;
import me.definedoddy.engine.input.Keyboard;
import me.definedoddy.engine.input.Mouse;
import me.definedoddy.engine.rendering.camera.Camera;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class FPCamera extends Camera {
    private final float movementSpeed = 0.15f;
    private final float mouseSensitivity = 0.15f;

    @Override
    public void init() {
        setPosition(new Vector3f(0f, 3f, -3f));
        setYaw(90f);
        super.init();
    }

    @Override
    public void update() {
        calcMovement();
        super.update();
    }

    private void calcMovement() {
        float forward = 0f, strafe = 0f;
        if (Keyboard.get().isKeyPressed(KeyCode.W)) forward += 1f;
        if (Keyboard.get().isKeyPressed(KeyCode.S)) forward -= 1f;
        if (Keyboard.get().isKeyPressed(KeyCode.A)) strafe -= 1f;
        if (Keyboard.get().isKeyPressed(KeyCode.D)) strafe += 1f;

        travel(forward, strafe);

        Vector2f mouseDelta = Mouse.get().getDelta();
        float rotX = mouseDelta.y * mouseSensitivity;
        float rotY = mouseDelta.x * mouseSensitivity;
        rotate(rotX, rotY, 0f);
    }

    private void travel(float forward, float strafe) {
        if (forward == 0 && strafe == 0) return;

        float yawRad = (float) Math.toRadians(getYaw());
        float x = (float) (strafe * Math.cos(yawRad) + forward * Math.sin(yawRad));
        float z = (float) (strafe * Math.sin(yawRad) - forward * Math.cos(yawRad));

        Vector3f vector = new Vector3f(x, 0, z);
        vector.normalize();
        vector.mul(movementSpeed);

        getPosition().add(vector);
    }
}
