package me.definedoddy.dreamWeavers.player;

import me.definedoddy.engine.context.Time;
import me.definedoddy.engine.input.Mouse;
import me.definedoddy.engine.rendering.camera.Camera;
import org.joml.Vector2f;

public class FPSCamera extends Camera {
    private float mouseSensitivity = 10f;
    private float mouseSmoothing = 2f;

    @Override
    public void init() {
        Mouse.get().setCursorVisible(false);
        Mouse.get().centerCursor();

        super.init();
    }

    @Override
    public void update() {
        calcRotation();
        super.update();
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

    @Override
    public void setPitch(float pitch) {
        super.setPitch(pitch);
        if (getPitch() > 90) setPitch(90);
        if (getPitch() < -90) setPitch(-90);
    }

    public void setMouseSensitivity(float mouseSensitivity) {
        this.mouseSensitivity = mouseSensitivity;
    }

    public void setMouseSmoothing(float mouseSmoothing) {
        this.mouseSmoothing = mouseSmoothing;
    }

    public float getMouseSensitivity() {
        return mouseSensitivity;
    }

    public float getMouseSmoothing() {
        return mouseSmoothing;
    }
}
