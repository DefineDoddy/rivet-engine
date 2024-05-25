package me.definedoddy.engine.rendering.shader.custom;

import me.definedoddy.engine.rendering.shader.Uniform;
import me.definedoddy.engine.rendering.texture.Material;
import me.definedoddy.toolkit.debug.Debug;
import org.lwjgl.opengl.GL20;

public class UniformMaterial extends Uniform {
    public UniformMaterial(String name) {
        super(name);
    }

    public void loadMaterial(Material material) {
        //material.bind();

        // Load textures
        int diffuseId = material.getDiffuse() != null ? material.getDiffuse().getId() : -1;
        //GL20.glUniform1i(getLocationOf(getProgramID(), "diffuse"), diffuseId);
        GL20.glUniform1i(getLocationOf(getProgramId(), "has_diffuse"), diffuseId != -1 ? 1 : 0);

        int normalId = material.getNormal() != null ? material.getNormal().getId() : -1;
        //GL20.glUniform1i(getLocationOf(getProgramID(), "normal"), normalId);
        GL20.glUniform1i(getLocationOf(getProgramId(), "has_normal"), normalId != -1 ? 1 : 0);

        int specularId = material.getSpecular() != null ? material.getSpecular().getId() : -1;
        //GL20.glUniform1i(getLocationOf(getProgramID(), "specular"), specularId);
        GL20.glUniform1i(getLocationOf(getProgramId(), "has_specular"), specularId != -1 ? 1 : 0);

        // Load properties
        /*GL20.glUniform3f(getLocationOf(getProgramID(), "tint_colour"),
                material.getTintColour().getRed(),
                material.getTintColour().getGreen(),
                material.getTintColour().getBlue()
        );*/

        GL20.glUniform1f(getLocationOf(getProgramId(), "shininess"), material.getShininess());

        //material.unbind();
    }

    private int getLocationOf(int programID, String property) {
        int loc = GL20.glGetUniformLocation(programID, getName() + "." + property);
        if (loc == -1) Debug.logError("Could not find uniform variable: " + getName() + "." + property);
        return loc;
    }
}
