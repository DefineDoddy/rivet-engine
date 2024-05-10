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
    private final UniformBool useTexture = new UniformBool("use_texture");

    private final UniformMatrix4f transformMatrix = new UniformMatrix4f("transform_matrix");
    private final UniformMatrix4f projectionMatrix = new UniformMatrix4f("projection_matrix");
    private final UniformMatrix4f viewMatrix = new UniformMatrix4f("view_matrix");

    private final UniformArray lightPositions = new UniformArray("light_positions", UniformArray.Type.VEC3F);
    private final UniformArray lightColours = new UniformArray("light_colours", UniformArray.Type.COLOUR);
    private final UniformArray lightInnerRadii = new UniformArray("light_inner_radii", UniformArray.Type.FLOAT);
    private final UniformArray lightOuterRadii = new UniformArray("light_outer_radii", UniformArray.Type.FLOAT);

    public EntityShader(ProjectFile vertexFile, ProjectFile fragmentFile) {
        super(vertexFile, fragmentFile, "position", "tex_coords", "normal");
    }

    public static EntityShader create() {
        EntityShader shader = new EntityShader(VERTEX_SHADER, FRAGMENT_SHADER);
        shader.setUniforms(
                shader.colour, shader.texture, shader.useTexture,
                shader.transformMatrix, shader.projectionMatrix, shader.viewMatrix,
                shader.lightPositions, shader.lightColours, shader.lightInnerRadii, shader.lightOuterRadii
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

    public UniformBool getUseTexture() {
        return useTexture;
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

    public UniformArray getLightPositions() {
        return lightPositions;
    }

    public UniformArray getLightColours() {
        return lightColours;
    }

    public UniformArray getLightInnerRadii() {
        return lightInnerRadii;
    }

    public UniformArray getLightOuterRadii() {
        return lightOuterRadii;
    }
}
