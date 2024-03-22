package me.definedoddy.engine.rendering.entity;

import me.definedoddy.engine.rendering.shader.Shader;
import me.definedoddy.toolkit.file.ProjectFile;

public class EntityShader extends Shader {
    private static final ProjectFile FOLDER = new ProjectFile("engine/rendering/entity");
    private static final ProjectFile VERTEX_SHADER = new ProjectFile(FOLDER, "entity_vertex.glsl");
    private static final ProjectFile FRAGMENT_SHADER = new ProjectFile(FOLDER, "entity_fragment.glsl");

    public EntityShader(ProjectFile vertexFile, ProjectFile fragmentFile) {
        super(vertexFile, fragmentFile, "in_position", "in_colour");
    }

    public static EntityShader create() {
        EntityShader shader = new EntityShader(VERTEX_SHADER, FRAGMENT_SHADER);
        shader.validate();
        return shader;
    }
}
