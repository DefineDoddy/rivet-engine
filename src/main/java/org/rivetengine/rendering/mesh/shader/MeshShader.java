package org.rivetengine.rendering.mesh.shader;

import org.rivetengine.rendering.Rendering;
import org.rivetengine.rendering.shader.Shader;
import org.rivetengine.rendering.shader.uniform.UniformInt;
import org.rivetengine.rendering.shader.uniform.UniformMatrix4f;
import org.rivetengine.rendering.shader.uniform.custom.UniformLights;
import org.rivetengine.rendering.shader.uniform.custom.UniformMaterial;
import org.rivetengine.toolkit.file.ProjectFile;

public class MeshShader extends Shader {
    private static final ProjectFile FOLDER = new ProjectFile("engine/rendering/mesh/shader");
    private static final ProjectFile VERTEX_SHADER = new ProjectFile(FOLDER, "mesh_vertex.glsl");
    private static final ProjectFile FRAGMENT_SHADER = new ProjectFile(FOLDER, "mesh_fragment.glsl");

    // Uniform variables
    public final UniformMatrix4f transformMatrix = new UniformMatrix4f("transform_matrix");
    public final UniformMatrix4f projectionMatrix = new UniformMatrix4f("projection_matrix");
    public final UniformMatrix4f viewMatrix = new UniformMatrix4f("view_matrix");

    public final UniformMaterial meshMaterial = new UniformMaterial("mesh_material");

    public final UniformLights lights = new UniformLights("lights");
    public final UniformInt lightCount = new UniformInt("light_count");

    public MeshShader(ProjectFile vertexFile, ProjectFile fragmentFile) {
        super(vertexFile, fragmentFile, "position", "tex_coords", "normal");
    }

    public static MeshShader create() {
        MeshShader shader = new MeshShader(VERTEX_SHADER, FRAGMENT_SHADER);

        // Set shader variables
        shader.setVariable("MAX_LIGHTS", String.valueOf(Rendering.MAX_LIGHTS));
        shader.compile();

        shader.setUniforms(
                shader.transformMatrix, shader.projectionMatrix, shader.viewMatrix,
                shader.meshMaterial, shader.lights, shader.lightCount);

        shader.validate();
        return shader;
    }
}
