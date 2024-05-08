package me.definedoddy.engine.rendering.entity;

import me.definedoddy.engine.rendering.shader.*;
import me.definedoddy.toolkit.file.ProjectFile;

public class EntityShader extends Shader {
    private static final ProjectFile FOLDER = new ProjectFile("engine/rendering/entity");
    private static final ProjectFile VERTEX_SHADER = new ProjectFile(FOLDER, "entity_vertex.glsl");
    private static final ProjectFile FRAGMENT_SHADER = new ProjectFile(FOLDER, "entity_fragment.glsl");

    // Uniform variables
    private final UniformColour colour = new UniformColour("tint_colour");
    private final UniformTexture texture = new UniformTexture("tex");

    private final UniformMatrix4f transformMatrix = new UniformMatrix4f("transform_matrix");
    private final UniformMatrix4f projectionMatrix = new UniformMatrix4f("projection_matrix");
    private final UniformMatrix4f viewMatrix = new UniformMatrix4f("view_matrix");

    private final UniformVec3f lightPosition = new UniformVec3f("light_position");
    private final UniformColour lightColour = new UniformColour("light_colour");

    public EntityShader(ProjectFile vertexFile, ProjectFile fragmentFile) {
        super(vertexFile, fragmentFile, "position", "tex_coords", "normal");
    }

    public static EntityShader create() {
        EntityShader shader = new EntityShader(VERTEX_SHADER, FRAGMENT_SHADER);
        shader.setUniforms(
                shader.colour, shader.texture,
                shader.transformMatrix, shader.projectionMatrix, shader.viewMatrix,
                shader.lightColour
        );
        shader.validate();
        return shader;
    }

    public UniformColour getColour() {
        return colour;
    }

    public UniformTexture getTexture() {
        return texture;
    }

    public UniformMatrix4f getTransformationMatrix() {
        return transformMatrix;
    }

    public UniformMatrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public UniformMatrix4f getViewMatrix() {
        return viewMatrix;
    }

    public UniformVec3f getLightPosition() {
        return lightPosition;
    }

    public UniformColour getLightColour() {
        return lightColour;
    }
}
