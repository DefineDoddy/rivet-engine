package com.rivetengine.game.entity.player.camera.shake;

import com.rivetengine.engine.core.Time;
import com.rivetengine.engine.entity.components.rendering.Camera;
import com.rivetengine.toolkit.maths.Axis3d;

import java.util.ArrayList;
import java.util.List;

public class ShakeController {
    private final Camera camera;
    private final List<CameraShake> shakes;

    public ShakeController(Camera camera) {
        this.camera = camera;
        this.shakes = new ArrayList<>();
    }

    public void update() {
        for (int i = shakes.size() - 1; i >= 0; i--) {
            CameraShake shake = shakes.get(i);

            if (!shake.isStarted())
                shake.startShake();
            shake.update((float) Time.getDeltaTime());

            float posX = 0f, posY = 0f, posZ = 0f;
            float rotX = 0f, rotY = 0f, rotZ = 0f;

            if (shake.getPosAxis().has(Axis3d.X))
                posX = shake.getShakeOffset();
            if (shake.getPosAxis().has(Axis3d.Y))
                posY = shake.getShakeOffset();
            if (shake.getPosAxis().has(Axis3d.Z))
                posZ = shake.getShakeOffset();

            if (shake.getRotAxis().has(Axis3d.X))
                rotX = shake.getShakeOffset();
            if (shake.getRotAxis().has(Axis3d.Y))
                rotY = shake.getShakeOffset();
            if (shake.getRotAxis().has(Axis3d.Z))
                rotZ = shake.getShakeOffset();

            camera.setLocalPosition(camera.getPosition().add(posX, posY, posZ));
            camera.setLocalRotation(camera.getRotation().add(rotX, rotY, rotZ));

            if (!shake.isShaking()) {
                camera.setLocalPosition(camera.getPosition().sub(posX, posY, posZ));
                camera.setLocalRotation(camera.getRotation().sub(rotX, rotY, rotZ));
                shakes.remove(i);
            }
        }
    }

    public void addShake(CameraShake shake) {
        shakes.add(shake);
    }
}
