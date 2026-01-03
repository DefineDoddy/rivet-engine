package org.rivetengine.rendering.postprocessing.chromaticaberration;

import org.rivetengine.rendering.postprocessing.shader.PostProcessShader;
import org.rivetengine.rendering.shader.uniform.UniformFloat;
import org.rivetengine.toolkit.file.ProjectFile;

public class ChromaticAberrationShader extends PostProcessShader {
    private static final ProjectFile FRAGMENT_SHADER = new ProjectFile(
            "rendering/postprocessing/chromaticaberration/chromatic_aberration_fragment.glsl");

    public final UniformFloat intensity = new UniformFloat("intensity");

    public ChromaticAberrationShader() {
        super(FRAGMENT_SHADER);
    }

    public static ChromaticAberrationShader create() {
        ChromaticAberrationShader shader = new ChromaticAberrationShader();
        shader.compile();
        shader.setUniforms(shader.colourTexture, shader.intensity);
        shader.validate();
        return shader;
    }
}
