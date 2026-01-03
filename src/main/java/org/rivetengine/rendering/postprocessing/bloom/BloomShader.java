package org.rivetengine.rendering.postprocessing.bloom;

import org.rivetengine.rendering.postprocessing.shader.PostProcessShader;
import org.rivetengine.rendering.shader.uniform.UniformFloat;
import org.rivetengine.toolkit.file.ProjectFile;

public class BloomShader extends PostProcessShader {
    private static final ProjectFile FRAGMENT_SHADER = new ProjectFile("rendering/postprocessing/bloom/bloom_fragment.glsl");

    public final UniformFloat threshold = new UniformFloat("threshold");
    public final UniformFloat intensity = new UniformFloat("intensity");

    public BloomShader() {
        super(FRAGMENT_SHADER);
    }

    public static BloomShader create() {
        BloomShader shader = new BloomShader();
        shader.compile();
        shader.setUniforms(shader.colourTexture, shader.threshold, shader.intensity);
        shader.validate();
        return shader;
    }
}

