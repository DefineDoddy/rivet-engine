package com.rivetengine.game.entity.player.camera;

import com.rivetengine.engine.core.Time;
import com.rivetengine.engine.entity.components.rendering.Camera;
import com.rivetengine.engine.input.Mouse;
import com.rivetengine.engine.window.GameWindow;
import com.rivetengine.game.entity.player.camera.shake.CameraShake;
import com.rivetengine.game.entity.player.camera.shake.ShakeController;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class FPCamera extends Camera {
    private Vector3f rotation = new Vector3f();
    private final float mouseSensitivity = 6f;
    private final float mouseSmoothing = 2f;

    private final ShakeController shaker;

    public FPCamera(Vector3f position) {
        super(position);
        this.shaker = new ShakeController(this);
    }

    @Override
    public void init() {
        Mouse.get().setCursorPosition(GameWindow.get().getWidth() / 2f, GameWindow.get().getHeight() / 2f);
        Mouse.get().setCursorVisible(false);
        setLocalRotation(new Vector3f(getPitch(), 90, 0));
        setLocalPosition(new Vector3f(0f, 3.6f, 0f));
        super.init();
    }

    @Override
    public void update() {
        calcRotation();
        shaker.update();
        super.update();
    }

    private void calcRotation() {
        Vector2f mouseDelta = Mouse.get().getDelta();
        if (mouseDelta.x == 0 && mouseDelta.y == 0)
            return;

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
        if (getPitch() > 90)
            setPitch(90);
        if (getPitch() < -90)
            setPitch(-90);
    }

    public void shake(CameraShake shake) {
        shaker.addShake(shake);
    }
}
