package me.definedoddy.engine.rendering.basicMesh;

import me.definedoddy.engine.rendering.shader.Shader;
import me.definedoddy.engine.rendering.shader.UniformColour;
import me.definedoddy.toolkit.file.ProjectFile;

public class BasicMeshShader extends Shader {
    private static final ProjectFile FOLDER = new ProjectFile("engine/rendering/basicMesh");
    private static final ProjectFile VERTEX_SHADER = new ProjectFile(FOLDER, "basicMesh_vertex.glsl");
    private static final ProjectFile FRAGMENT_SHADER = new ProjectFile(FOLDER, "basicMesh_fragment.glsl");

    private final UniformColour colour = new UniformColour("colour");

    public BasicMeshShader(ProjectFile vertexFile, ProjectFile fragmentFile) {
        super(vertexFile, fragmentFile, "in_position");
    }

    public static BasicMeshShader create() {
        BasicMeshShader shader = new BasicMeshShader(VERTEX_SHADER, FRAGMENT_SHADER);
        shader.setUniforms(shader.colour);
        shader.validate();
        return shader;
    }

    public UniformColour getColour() {
        return colour;
    }
}
