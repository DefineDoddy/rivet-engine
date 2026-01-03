package org.rivetengine.rendering.postprocessing.colourgrading;

import org.rivetengine.rendering.postprocessing.PostProcessEffect;

public class ColourGrading extends PostProcessEffect {
    public float exposure = 1f;
    public float contrast = 1f;
    public float saturation = 1f;

    public ColourGrading() {
    }

    public ColourGrading(float exposure, float contrast, float saturation) {
        this.exposure = exposure;
        this.contrast = contrast;
        this.saturation = saturation;
    }
}
