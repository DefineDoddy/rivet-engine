package me.definedoddy.engine.rendering.model;

import me.definedoddy.engine.manager.GameManager;
import me.definedoddy.engine.rendering.shader.*;
import me.definedoddy.toolkit.file.ProjectFile;

public class ModelShader extends Shader {
    private static final ProjectFile FOLDER = new ProjectFile("engine/rendering/model");
    private static final ProjectFile VERTEX_SHADER = new ProjectFile(FOLDER, "model_vertex.glsl");
    private static final ProjectFile FRAGMENT_SHADER = new ProjectFile(FOLDER, "model_fragment_old.glsl");

    // Uniform variables
    private final UniformColour colour = new UniformColour("tint_colour");
    private final UniformBool useTexture = new UniformBool("use_texture");

    private final UniformMatrix4f transformMatrix = new UniformMatrix4f("transform_matrix");
    private final UniformMatrix4f projectionMatrix = new UniformMatrix4f("projection_matrix");
    private final UniformMatrix4f viewMatrix = new UniformMatrix4f("view_matrix");

    private final UniformInt numLights = new UniformInt("num_lights");
    private final UniformArray lightPositions = new UniformArray("light_positions", UniformArray.Type.VEC3F);
    private final UniformArray lightColours = new UniformArray("light_colours", UniformArray.Type.COLOUR);
    private final UniformArray lightAttenuations = new UniformArray("light_attenuations", UniformArray.Type.VEC3F);
    private final UniformFloat ambientLight = new UniformFloat("ambient_light");
    private final UniformFloat reflectivity = new UniformFloat("reflectivity");

    public ModelShader(ProjectFile vertexFile, ProjectFile fragmentFile) {
        super(vertexFile, fragmentFile, "position", "tex_coords", "normal");
    }

    public static ModelShader create() {
        ModelShader shader = new ModelShader(VERTEX_SHADER, FRAGMENT_SHADER);

        // Set shader variables
        int maxLights = GameManager.getRenderEngine().getRenderConfig().getMaxLightsOnMesh();
        shader.setVariable("MAX_LIGHTS", String.valueOf(maxLights));

        shader.compile();

        shader.setUniforms(
                shader.colour, shader.useTexture,
                shader.transformMatrix, shader.projectionMatrix, shader.viewMatrix,
                shader.numLights, shader.lightPositions, shader.lightColours, shader.lightAttenuations,
                shader.ambientLight, shader.reflectivity
        );

        shader.validate();
        return shader;
    }

    public UniformColour getColour() {
        return colour;
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

    public UniformInt getNumLights() {
        return numLights;
    }

    public UniformArray getLightPositions() {
        return lightPositions;
    }

    public UniformArray getLightColours() {
        return lightColours;
    }

    public UniformArray getLightAttenuations() {
        return lightAttenuations;
    }

    public UniformFloat getAmbientLight() {
        return ambientLight;
    }

    public UniformFloat getReflectivity() {
        return reflectivity;
    }
}
