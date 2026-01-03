package org.rivetengine.rendering.postprocessing.vignette;

import org.rivetengine.rendering.postprocessing.PostProcessEffect;

public class Vignette extends PostProcessEffect {
    public float intensity = 0.1f;
    public float smoothness = 0.5f;

    public Vignette() {
    }

    public Vignette(float intensity, float smoothness) {
        this.intensity = intensity;
        this.smoothness = smoothness;
    }
}
