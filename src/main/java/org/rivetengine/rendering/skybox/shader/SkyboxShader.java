package org.rivetengine.rendering.skybox.shader;

import org.rivetengine.rendering.shader.Shader;
import org.rivetengine.rendering.shader.uniform.UniformFloat;
import org.rivetengine.rendering.shader.uniform.UniformMatrix4f;
import org.rivetengine.toolkit.file.ProjectFile;

public class SkyboxShader extends Shader {
    private static final ProjectFile FOLDER = new ProjectFile("rendering/skybox/shader");
    private static final ProjectFile VERTEX_SHADER = new ProjectFile(FOLDER, "skybox_vertex.glsl");
    private static final ProjectFile FRAGMENT_SHADER = new ProjectFile(FOLDER, "skybox_fragment.glsl");

    public final UniformMatrix4f projectionMatrix = new UniformMatrix4f("projection_matrix");
    public final UniformMatrix4f viewMatrix = new UniformMatrix4f("view_matrix");
    public final UniformFloat rotation = new UniformFloat("rotation");

    public SkyboxShader(ProjectFile vertexFile, ProjectFile fragmentFile) {
        super(vertexFile, fragmentFile, "tex_coords");
    }

    public static SkyboxShader create() {
        SkyboxShader shader = new SkyboxShader(VERTEX_SHADER, FRAGMENT_SHADER);

        shader.compile();
        shader.setUniforms(shader.projectionMatrix, shader.viewMatrix, shader.rotation);
        shader.validate();

        return shader;
    }
}
