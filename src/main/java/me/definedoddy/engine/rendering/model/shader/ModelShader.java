package me.definedoddy.engine.rendering.model.shader;

import me.definedoddy.engine.manager.GameManager;
import me.definedoddy.engine.rendering.shader.Shader;
import me.definedoddy.engine.rendering.shader.uniform.UniformInt;
import me.definedoddy.engine.rendering.shader.uniform.UniformMatrix4f;
import me.definedoddy.engine.rendering.shader.uniform.custom.UniformLights;
import me.definedoddy.engine.rendering.shader.uniform.custom.UniformMaterial;
import me.definedoddy.toolkit.file.ProjectFile;

public class ModelShader extends Shader {
    private static final ProjectFile FOLDER = new ProjectFile("engine/rendering/model/shader");
    private static final ProjectFile VERTEX_SHADER = new ProjectFile(FOLDER, "model_vertex.glsl");
    private static final ProjectFile FRAGMENT_SHADER = new ProjectFile(FOLDER, "model_fragment.glsl");

    // Uniform variables
    private final UniformMatrix4f transformMatrix = new UniformMatrix4f("transform_matrix");
    private final UniformMatrix4f projectionMatrix = new UniformMatrix4f("projection_matrix");
    private final UniformMatrix4f viewMatrix = new UniformMatrix4f("view_matrix");

    private final UniformMaterial meshMaterial = new UniformMaterial("mesh_material");
    private final UniformMaterial modelMaterial = new UniformMaterial("model_material");

    private final UniformLights lights = new UniformLights("lights");
    private final UniformInt lightCount = new UniformInt("light_count");

    public ModelShader(ProjectFile vertexFile, ProjectFile fragmentFile) {
        super(vertexFile, fragmentFile, "position", "tex_coords", "normal");
    }

    public static ModelShader create() {
        ModelShader shader = new ModelShader(VERTEX_SHADER, FRAGMENT_SHADER);

        // Set shader variables
        int maxLights = GameManager.getRenderEngine().getRenderConfig().getMaxDirectionalLights();
        shader.setVariable("MAX_LIGHTS", String.valueOf(maxLights));

        shader.compile();

        shader.setUniforms(
                shader.transformMatrix, shader.projectionMatrix, shader.viewMatrix,
                shader.meshMaterial, shader.modelMaterial, shader.lights, shader.lightCount
        );

        shader.validate();
        return shader;
    }

    public UniformMatrix4f getTransformMatrix() {
        return transformMatrix;
    }

    public UniformMatrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public UniformMatrix4f getViewMatrix() {
        return viewMatrix;
    }

    public UniformMaterial getMeshMaterial() {
        return meshMaterial;
    }

    public UniformMaterial getModelMaterial() {
        return modelMaterial;
    }

    public UniformLights getLights() {
        return lights;
    }

    public UniformInt getLightCount() {
        return lightCount;
    }
}
