package org.rivetengine.rendering.shader.uniform.custom;

import org.rivetengine.rendering.material.Material;
import org.rivetengine.rendering.shader.uniform.Uniform;
import org.lwjgl.opengl.GL20;

public class UniformMaterial extends Uniform {
    public UniformMaterial(String name) {
        super(name);
    }

    public void loadMaterial(Material material) {
        // Load textures
        int diffuseId = material.diffuseMap != null ? material.diffuseMap.id : -1;
        // GL20.glUniform1i(getLocationOf(programId, "diffuse"), diffuseId);
        GL20.glUniform1i(getLocationOf(programId, "has_diffuse"), diffuseId != -1 ? 1 : 0);

        int normalId = material.normalMap != null ? material.normalMap.id : -1;
        // GL20.glUniform1i(getLocationOf(programId, "normal"), normalId);
        GL20.glUniform1i(getLocationOf(programId, "has_normal"), normalId != -1 ? 1 : 0);

        int specularId = material.specularMap != null ? material.specularMap.id : -1;
        // GL20.glUniform1i(getLocationOf(programId, "specular"), specularId);
        GL20.glUniform1i(getLocationOf(programId, "has_specular"), specularId != -1 ? 1 : 0);

        // Load properties
        GL20.glUniform3f(getLocationOf(programId, "tint_colour"),
                material.colour.getRed() / 255f,
                material.colour.getGreen() / 255f,
                material.colour.getBlue() / 255f);

        GL20.glUniform1i(getLocationOf(programId, "transparent"), material.transparent ? 1 : 0);
        GL20.glUniform1f(getLocationOf(programId, "shininess"), material.shininess);
    }

    private int getLocationOf(int programID, String property) {
        int loc = GL20.glGetUniformLocation(programID, name + "." + property);
        // if (loc == -1) Debug.logError("Could not find uniform variable: " + getName()
        // + "." + property);
        return loc;
    }
}
