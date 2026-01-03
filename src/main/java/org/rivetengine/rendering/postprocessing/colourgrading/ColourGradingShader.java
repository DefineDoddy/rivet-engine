package org.rivetengine.rendering.postprocessing.colourgrading;

import org.rivetengine.rendering.postprocessing.shader.PostProcessShader;
import org.rivetengine.rendering.shader.uniform.UniformFloat;
import org.rivetengine.toolkit.file.ProjectFile;

public class ColourGradingShader extends PostProcessShader {
    private static final ProjectFile FRAGMENT_SHADER = new ProjectFile(
            "rendering/postprocessing/colourgrading/colour_grading_fragment.glsl");

    public final UniformFloat exposure = new UniformFloat("exposure");
    public final UniformFloat contrast = new UniformFloat("contrast");
    public final UniformFloat saturation = new UniformFloat("saturation");

    public ColourGradingShader() {
        super(FRAGMENT_SHADER);
    }

    public static ColourGradingShader create() {
        ColourGradingShader shader = new ColourGradingShader();
        shader.compile();
        shader.setUniforms(shader.colourTexture, shader.exposure, shader.contrast, shader.saturation);
        shader.validate();
        return shader;
    }
}
