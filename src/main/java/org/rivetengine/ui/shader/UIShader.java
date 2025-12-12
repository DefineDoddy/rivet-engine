package org.rivetengine.ui.shader;

import org.rivetengine.rendering.shader.Shader;
import org.rivetengine.rendering.shader.uniform.UniformVec2f;
import org.rivetengine.rendering.shader.uniform.UniformVec4f;
import org.rivetengine.toolkit.file.ProjectFile;

public class UIShader extends Shader {
    private static final ProjectFile FOLDER = new ProjectFile("engine/ui/shader");
    private static final ProjectFile VERTEX_SHADER = new ProjectFile(FOLDER, "ui_vertex.glsl");
    private static final ProjectFile FRAGMENT_SHADER = new ProjectFile(FOLDER, "ui_fragment.glsl");

    // Uniform variables
    private final UniformVec4f transform = new UniformVec4f("transform");
    private final UniformVec2f uiDimensions = new UniformVec2f("ui_dimensions");

    public UIShader(ProjectFile vertexFile, ProjectFile fragmentFile) {
        super(vertexFile, fragmentFile, "position", "tex_coords");
    }

    public static UIShader create() {
        UIShader shader = new UIShader(VERTEX_SHADER, FRAGMENT_SHADER);
        shader.compile();

        shader.setUniforms(shader.transform);

        shader.validate();
        return shader;
    }

    public UniformVec4f getTransform() {
        return transform;
    }

    public UniformVec2f getUIDimensions() {
        return uiDimensions;
    }
}
