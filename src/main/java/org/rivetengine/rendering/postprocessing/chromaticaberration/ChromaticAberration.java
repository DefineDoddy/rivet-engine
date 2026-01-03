package org.rivetengine.rendering.postprocessing.chromaticaberration;

import org.rivetengine.rendering.postprocessing.PostProcessEffect;

public class ChromaticAberration extends PostProcessEffect {
    public float intensity = 0.05f;

    public ChromaticAberration() {
    }

    public ChromaticAberration(float intensity) {
        this.intensity = intensity;
    }
}
