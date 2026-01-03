package org.rivetengine.entity.components;

import org.joml.Vector3f;
import org.rivetengine.entity.component.Component;

public class Shaker implements Component {
    public Vector3f positionIntensity = new Vector3f();
    public Vector3f rotationIntensity = new Vector3f();
    public float duration;
    public float frequency = 25f;
    public float elapsed;
    public float seed;

    public Vector3f offset = new Vector3f();
    public Vector3f rotationOffset = new Vector3f();

    public void shake(float intensity, float duration) {
        shake(new Vector3f(intensity), new Vector3f(), duration, 25f);
    }

    public void shake(Vector3f posIntensity, Vector3f rotIntensity, float duration) {
        shake(posIntensity, rotIntensity, duration, 25f);
    }

    public void shake(Vector3f posIntensity, Vector3f rotIntensity, float duration, float frequency) {
        this.positionIntensity.set(posIntensity);
        this.rotationIntensity.set(rotIntensity);
        this.duration = duration;
        this.frequency = frequency;
        this.elapsed = 0f;
        this.seed = (float) Math.random() * 1000f;
    }

    public boolean isActive() {
        return elapsed < duration;
    }

    public void stop() {
        this.duration = 0;
        this.elapsed = 0;
        this.offset.set(0);
        this.rotationOffset.set(0);
    }
}
