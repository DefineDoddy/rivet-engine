package org.rivetengine.rendering.postprocessing.vignette;

import org.rivetengine.rendering.postprocessing.shader.PostProcessShader;
import org.rivetengine.rendering.shader.uniform.UniformFloat;
import org.rivetengine.toolkit.file.ProjectFile;

public class VignetteShader extends PostProcessShader {
    private static final ProjectFile FRAGMENT_SHADER = new ProjectFile(
            "rendering/postprocessing/vignette/vignette_fragment.glsl");

    public final UniformFloat intensity = new UniformFloat("intensity");
    public final UniformFloat smoothness = new UniformFloat("smoothness");

    public VignetteShader() {
        super(FRAGMENT_SHADER);
    }

    public static VignetteShader create() {
        VignetteShader shader = new VignetteShader();
        shader.compile();
        shader.setUniforms(shader.colourTexture, shader.intensity, shader.smoothness);
        shader.validate();
        return shader;
    }
}
