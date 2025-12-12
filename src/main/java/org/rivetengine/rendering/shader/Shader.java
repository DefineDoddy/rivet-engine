package org.rivetengine.rendering.shader;

import org.rivetengine.rendering.shader.uniform.Uniform;
import org.rivetengine.toolkit.file.ProjectFile;
import org.rivetengine.toolkit.memory.Disposable;

import java.util.HashMap;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

public class Shader implements Disposable {
    private final ProjectFile vertexFile;
    private final ProjectFile fragmentFile;
    private final String[] attributes;

    private final int programId;
    private final HashMap<String, String> variables = new HashMap<>();

    private List<Uniform> uniforms;

    private static Shader current;

    public static Shader getCurrent() {
        return current;
    }

    public Shader(ProjectFile vertexFile, ProjectFile fragmentFile, String... attributes) {
        this.vertexFile = vertexFile;
        this.fragmentFile = fragmentFile;
        this.attributes = attributes;

        programId = glCreateProgram();
    }

    public void compile() {
        int vertexShaderId = compileShader(vertexFile, GL_VERTEX_SHADER);
        int fragmentShaderId = compileShader(fragmentFile, GL_FRAGMENT_SHADER);

        glAttachShader(programId, vertexShaderId);
        glAttachShader(programId, fragmentShaderId);

        bindAttributes(attributes);
        glLinkProgram(programId);

        glDetachShader(programId, vertexShaderId);
        glDetachShader(programId, fragmentShaderId);
        glDeleteShader(vertexShaderId);
        glDeleteShader(fragmentShaderId);

        validate();
    }

    public int getProgramId() {
        return programId;
    }

    public void bind() {
        glUseProgram(programId);
        current = this;
    }

    public void unbind() {
        glUseProgram(0);
        current = null;
    }

    public void setUniforms(Uniform... uniforms) {
        this.uniforms = List.of(uniforms);
        for (Uniform uniform : uniforms) {
            uniform.setProgramId(programId);
        }
    }

    public <T extends Uniform> T getUniform(Class<T> type, String name) {
        for (Uniform uniform : uniforms) {
            if (uniform.getName().equals(name)) {
                return type.cast(uniform);
            }
        }
        return null;
    }

    public void setVariable(String name, String value) {
        variables.put(name, value);
    }

    public String getVariable(String name) {
        return variables.get(name);
    }

    public void validate() {
        glValidateProgram(programId);
        if (glGetProgrami(programId, GL_VALIDATE_STATUS) == GL_FALSE) {
            throw new RuntimeException("Failed to validate shader program: " + glGetProgramInfoLog(programId));
        }
    }

    private void bindAttributes(String[] attributes) {
        for (int i = 0; i < attributes.length; i++) {
            glBindAttribLocation(programId, i, attributes[i]);
        }
    }

    private int compileShader(ProjectFile file, int type) {
        String source = file.read();

        for (String variable : variables.keySet()) {
            source = source.replace("_" + variable + "_", variables.get(variable));
        }

        int shaderId = glCreateShader(type);

        glShaderSource(shaderId, source);
        glCompileShader(shaderId);

        int status = glGetShaderi(shaderId, GL_COMPILE_STATUS);
        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == GL_FALSE) {
            String message = glGetShaderInfoLog(shaderId, status);
            throw new RuntimeException("Failed to compile shader: " + file + "\n" + message);
        }

        return shaderId;
    }

    @Override
    public void dispose() {
        unbind();
        glDeleteProgram(programId);
    }
}
