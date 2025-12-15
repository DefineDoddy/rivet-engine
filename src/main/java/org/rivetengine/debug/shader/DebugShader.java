package org.rivetengine.debug.shader;

import org.rivetengine.rendering.shader.Shader;
import org.rivetengine.rendering.shader.uniform.UniformMatrix4f;
import org.rivetengine.toolkit.file.ProjectFile;

public class DebugShader extends Shader {
    private static final ProjectFile FOLDER = new ProjectFile("debug/shader");
    private static final ProjectFile VERTEX_SHADER = new ProjectFile(FOLDER, "debug_vertex.glsl");
    private static final ProjectFile FRAGMENT_SHADER = new ProjectFile(FOLDER, "debug_fragment.glsl");

    // Uniform variables
    public final UniformMatrix4f projectionMatrix = new UniformMatrix4f("projection_matrix");
    public final UniformMatrix4f viewMatrix = new UniformMatrix4f("view_matrix");

    public DebugShader(ProjectFile vertexFile, ProjectFile fragmentFile) {
        super(vertexFile, fragmentFile, "position", "colour");
    }

    public static DebugShader create() {
        DebugShader shader = new DebugShader(VERTEX_SHADER, FRAGMENT_SHADER);
        shader.compile();

        shader.setUniforms(shader.projectionMatrix, shader.viewMatrix);

        shader.validate();
        return shader;
    }
}
