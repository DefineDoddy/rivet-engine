package me.definedoddy.engine.debug.shader;

import me.definedoddy.engine.rendering.shader.Shader;
import me.definedoddy.engine.rendering.shader.uniform.UniformMatrix4f;
import me.definedoddy.toolkit.file.ProjectFile;

public class DebugShader extends Shader {
    private static final ProjectFile FOLDER = new ProjectFile("engine/debug/shader");
    private static final ProjectFile VERTEX_SHADER = new ProjectFile(FOLDER, "debug_vertex.glsl");
    private static final ProjectFile FRAGMENT_SHADER = new ProjectFile(FOLDER, "debug_fragment.glsl");

    // Uniform variables
    private final UniformMatrix4f projectionMatrix = new UniformMatrix4f("projection_matrix");
    private final UniformMatrix4f viewMatrix = new UniformMatrix4f("view_matrix");

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

    public UniformMatrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public UniformMatrix4f getViewMatrix() {
        return viewMatrix;
    }
}
