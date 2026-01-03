package org.rivetengine.rendering.postprocessing.shader;

import org.rivetengine.rendering.shader.Shader;
import org.rivetengine.rendering.shader.uniform.UniformInt;
import org.rivetengine.toolkit.file.ProjectFile;

public abstract class PostProcessShader extends Shader {
    private static final ProjectFile VERTEX_SHADER = new ProjectFile(
            "rendering/postprocessing/shader/post_process_vertex.glsl");

    public final UniformInt colourTexture = new UniformInt("colourTexture");

    public PostProcessShader(ProjectFile fragmentShader) {
        super(VERTEX_SHADER, fragmentShader, "position");
    }

    @Override
    public void bind() {
        super.bind();
        colourTexture.loadInt(0);
    }
}
