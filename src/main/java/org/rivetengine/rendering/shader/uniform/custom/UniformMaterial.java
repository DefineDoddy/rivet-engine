package org.rivetengine.rendering.shader.uniform.custom;

import org.rivetengine.core.Assets;
import org.rivetengine.entity.components.rendering.Material;
import org.rivetengine.rendering.shader.uniform.Uniform;
import org.rivetengine.rendering.texture.Texture;
import org.lwjgl.opengl.GL20;

public class UniformMaterial extends Uniform {
    public UniformMaterial(String name) {
        super(name);
    }

    public void loadMaterial(Material material) {
        Texture diffuseTex = material.diffuseMap != null ? Assets.get(material.diffuseMap) : null;
        Texture normalTex = material.normalMap != null ? Assets.get(material.normalMap) : null;
        Texture specularTex = material.specularMap != null ? Assets.get(material.specularMap) : null;

        int diffuseId = diffuseTex != null ? diffuseTex.id : -1;
        GL20.glUniform1i(getLocationOf(programId, "has_diffuse"), diffuseId != -1 ? 1 : 0);

        int normalId = normalTex != null ? normalTex.id : -1;
        GL20.glUniform1i(getLocationOf(programId, "has_normal"), normalId != -1 ? 1 : 0);

        int specularId = specularTex != null ? specularTex.id : -1;
        GL20.glUniform1i(getLocationOf(programId, "has_specular"), specularId != -1 ? 1 : 0);

        // Load properties
        GL20.glUniform3f(getLocationOf(programId, "tint_colour"),
                material.diffuse.getRed() / 255f,
                material.diffuse.getGreen() / 255f,
                material.diffuse.getBlue() / 255f);

        // GL20.glUniform1i(getLocationOf(programId, "transparent"),
        // material.transparent ? 1 : 0);
        GL20.glUniform1f(getLocationOf(programId, "shininess"), material.shininess);
    }

    private int getLocationOf(int programID, String property) {
        int loc = GL20.glGetUniformLocation(programID, name + "." + property);
        // if (loc == -1) Debug.logError("Could not find uniform variable: " + getName()
        // + "." + property);
        return loc;
    }
}
