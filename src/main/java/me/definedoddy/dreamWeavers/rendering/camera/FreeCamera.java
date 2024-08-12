package me.definedoddy.dreamWeavers.rendering.camera;

import me.definedoddy.engine.input.KeyCode;
import me.definedoddy.engine.input.Keyboard;
import me.definedoddy.engine.input.Mouse;
import me.definedoddy.engine.physics.Time;
import me.definedoddy.engine.rendering.camera.Camera;
import me.definedoddy.engine.window.GameWindow;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class FreeCamera extends Camera {
    private final Vector3f velocity = new Vector3f();
    private final float movementSpeed = 0.1f;
    private final float acceleration = 0.2f;

    private Vector3f rotation = new Vector3f();
    private final float mouseSensitivity = 10f;
    private final float mouseSmoothing = 2f;

    @Override
    public void init() {
        Mouse.get().setCursorPosition(GameWindow.get().getWidth() / 2f, GameWindow.get().getHeight() / 2f);
        Mouse.get().setCursorVisible(false);

        setPosition(new Vector3f(0f, 3f, -3f));
        setYaw(90f);

        rotation = new Vector3f(getPitch(), getYaw(), 0);

        super.init();
    }

    @Override
    public void update() {
        calcMovement();
        calcRotation();
        calcVelocity();
        super.update();
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

    private void calcRotation() {
        Vector2f mouseDelta = Mouse.get().getDelta();
        if (mouseDelta.x == 0 && mouseDelta.y == 0) return;

        if (mouseDelta.lengthSquared() > 0) {
            float rotX = mouseDelta.y * mouseSensitivity * (float) Time.getDeltaTime();
            float rotY = mouseDelta.x * mouseSensitivity * (float) Time.getDeltaTime();

            rotation.x += (rotX - rotation.x) / mouseSmoothing;
            rotation.y += (rotY - rotation.y) / mouseSmoothing;
        } else {
            rotation.x += (0 - rotation.x) / mouseSmoothing;
            rotation.y += (0 - rotation.y) / mouseSmoothing;
        }

        rotate(rotation.x, rotation.y, 0);
    }

    private void travel(float forward, float strafe, float up) {
        if (forward == 0 && strafe == 0 && up == 0) return;

        float yawRad = (float) Math.toRadians(getYaw());
        float x = (float) (strafe * Math.cos(yawRad) + forward * Math.sin(yawRad));
        float z = (float) (strafe * Math.sin(yawRad) - forward * Math.cos(yawRad));

        Vector3f vector = new Vector3f(x, up, z);
        vector.normalize().mul(movementSpeed);
        velocity.add(vector);
    }

    private void calcVelocity() {
        if (velocity.lengthSquared() > 0) {
            velocity.mul(1 - acceleration);
            if (velocity.lengthSquared() < 0.0001f) velocity.set(0);
            move(velocity);
        }
    }

    @Override
    public void setPitch(float pitch) {
        super.setPitch(pitch);
        if (getPitch() > 90) setPitch(90);
        if (getPitch() < -90) setPitch(-90);
    }
}
