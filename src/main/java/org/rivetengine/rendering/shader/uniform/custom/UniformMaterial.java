package org.rivetengine.rendering.shader.uniform.custom;

import org.rivetengine.rendering.shader.uniform.Uniform;
import org.rivetengine.rendering.texture.Material;
import org.lwjgl.opengl.GL20;

public class UniformMaterial extends Uniform {
    public UniformMaterial(String name) {
        super(name);
    }

    public void loadMaterial(Material material) {
        // Load textures
        int diffuseId = material.getDiffuseMap() != null ? material.getDiffuseMap().getId() : -1;
        //GL20.glUniform1i(getLocationOf(getProgramId(), "diffuse"), diffuseId);
        GL20.glUniform1i(getLocationOf(getProgramId(), "has_diffuse"), diffuseId != -1 ? 1 : 0);

        int normalId = material.getNormalMap() != null ? material.getNormalMap().getId() : -1;
        //GL20.glUniform1i(getLocationOf(getProgramId(), "normal"), normalId);
        GL20.glUniform1i(getLocationOf(getProgramId(), "has_normal"), normalId != -1 ? 1 : 0);

        int specularId = material.getSpecularMap() != null ? material.getSpecularMap().getId() : -1;
        //GL20.glUniform1i(getLocationOf(getProgramId(), "specular"), specularId);
        GL20.glUniform1i(getLocationOf(getProgramId(), "has_specular"), specularId != -1 ? 1 : 0);

        // Load properties
        GL20.glUniform3f(getLocationOf(getProgramId(), "tint_colour"),
                material.getColour().getRed() / 255f,
                material.getColour().getGreen() / 255f,
                material.getColour().getBlue() / 255f
        );

        GL20.glUniform1i(getLocationOf(getProgramId(), "transparent"), material.isTransparent() ? 1 : 0);
        GL20.glUniform1f(getLocationOf(getProgramId(), "shininess"), material.getShininess());
    }

    private int getLocationOf(int programID, String property) {
        int loc = GL20.glGetUniformLocation(programID, getName() + "." + property);
        //if (loc == -1) Debug.logError("Could not find uniform variable: " + getName() + "." + property);
        return loc;
    }
}
