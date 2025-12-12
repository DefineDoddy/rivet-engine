package me.definedoddy.game.entity.camera.shake;

import org.rivetengine.toolkit.maths.Axis3d;
import org.rivetengine.toolkit.types.EnumFlag;

public class CameraShake {
    private final float duration;
    private final float magnitude;

    private float elapsedTime;
    private boolean started;
    private boolean isShaking;

    private final EnumFlag<Axis3d> posAxis;
    private final EnumFlag<Axis3d> rotAxis;

    public CameraShake(float duration, float magnitude) {
        this.duration = duration;
        this.magnitude = magnitude;
        this.elapsedTime = 0f;
        this.isShaking = false;

        this.posAxis = new EnumFlag<>();
        this.rotAxis = new EnumFlag<>(Axis3d.class);
    }

    public void startShake() {
        if (started) return;
        this.started = true;
        this.isShaking = true;
        this.elapsedTime = 0f;
    }

    public void update(float deltaTime) {
        if (isShaking) {
            elapsedTime += deltaTime;
            if (elapsedTime >= duration) {
                isShaking = false;
                elapsedTime = 0f;
            }
        }
    }

    public boolean isStarted() {
        return started;
    }

    public boolean isShaking() {
        return isShaking;
    }

    public float getShakeOffset() {
        if (!isShaking) return 0f;

        float progress = elapsedTime / duration;
        float shakeAmount = (1 - progress) * magnitude;

        return (float) (Math.random() * shakeAmount * 2 - shakeAmount);
    }

    public CameraShake setPosAxis(Axis3d... axis) {
        posAxis.set(axis);
        return this;
    }

    public CameraShake setRotAxis(Axis3d... axis) {
        rotAxis.set(axis);
        return this;
    }

    public EnumFlag<Axis3d> getPosAxis() {
        return posAxis;
    }

    public EnumFlag<Axis3d> getRotAxis() {
        return rotAxis;
    }
}
