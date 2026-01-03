package org.rivetengine.rendering.postprocessing.bloom;

import org.rivetengine.rendering.postprocessing.PostProcessEffect;

public class Bloom extends PostProcessEffect {
    public float threshold = 0.5f;
    public float intensity = 0.1f;

    public Bloom() {
    }

    public Bloom(float intensity) {
        this.intensity = intensity;
    }
}
