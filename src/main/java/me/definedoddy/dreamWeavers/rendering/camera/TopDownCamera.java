package me.definedoddy.dreamWeavers.rendering.camera;

import me.definedoddy.engine.input.KeyCode;
import me.definedoddy.engine.input.Keyboard;
import me.definedoddy.engine.input.Mouse;
import me.definedoddy.engine.input.MouseBtn;
import me.definedoddy.engine.rendering.camera.Camera;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class TopDownCamera extends Camera {
    private final float movementSpeed = 0.4f;
    private final float mouseSensitivity = 0.1f;

    private Vector3f moveVelocity = new Vector3f();
    private Vector3f rotVelocity = new Vector3f();

    @Override
    public void init() {
        setPosition(new Vector3f(0f, 35f, 0f));
        setPitch(60f);
        super.init();
    }

    @Override
    public void update() {
        processMovement();
        super.update();
    }

    private void processMovement() {
        float forward = 0f, strafe = 0f;
        if (Keyboard.get().isKeyPressed(KeyCode.W)) forward += 1f;
        if (Keyboard.get().isKeyPressed(KeyCode.S)) forward -= 1f;
        if (Keyboard.get().isKeyPressed(KeyCode.A)) strafe -= 1f;
        if (Keyboard.get().isKeyPressed(KeyCode.D)) strafe += 1f;

        travel(forward, strafe);

        if (Mouse.get().isButtonPressed(MouseBtn.RIGHT)) {
            Vector2f rotation = Mouse.get().getDelta();
            rotate(0f, rotation.x * mouseSensitivity, 0f);
        }

        float scroll = -Mouse.get().getScrollY() * 2f;
        moveVelocity.y += scroll * 0.2f;
        rotate(scroll * 0.3f, 0f, 0f);

        moveVelocity = moveVelocity.mul(0.8f);
        getPosition().add(moveVelocity);
        setPosition(new Vector3f(getPosition().x, Math.min(Math.max(getPosition().y, 10f), 60f), getPosition().z));

        rotVelocity = rotVelocity.mul(0.8f);
        setPitch(getPitch() + rotVelocity.x);
        setYaw(getYaw() + rotVelocity.y);
        setRoll(getRoll() + rotVelocity.z);
        setPitch(Math.min(Math.max(getPitch(), 25f), 90f));
    }

    @Override
    public void rotate(float pitch, float yaw, float roll) {
        rotVelocity.add(new Vector3f(pitch, yaw, roll));
    }

    private void travel(float forward, float strafe) {
        if (forward == 0 && strafe == 0) return;

        float yawRad = (float) Math.toRadians(getYaw());
        float x = (float) (strafe * Math.cos(yawRad) + forward * Math.sin(yawRad));
        float z = (float) (strafe * Math.sin(yawRad) - forward * Math.cos(yawRad));

        Vector3f vector = new Vector3f(x, 0, z);
        vector.normalize();
        vector.mul(movementSpeed);

        moveVelocity.add(vector);
    }
}
