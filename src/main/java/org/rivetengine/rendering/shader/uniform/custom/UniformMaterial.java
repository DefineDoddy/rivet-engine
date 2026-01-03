package org.rivetengine.rendering.shader.uniform.custom;

import org.rivetengine.core.Assets;
import org.rivetengine.entity.components.rendering.Material;
import org.rivetengine.rendering.shader.uniform.Uniform;
import org.rivetengine.rendering.texture.Texture;
import org.lwjgl.opengl.GL20;

public class UniformMaterial extends Uniform {
    private final int baseUnit;

    public UniformMaterial(String name, int baseUnit) {
        super(name);
        this.baseUnit = baseUnit;
    }

    public void loadMaterial(Material material) {
        if (material == null) {
            material = new Material();
        }
        Texture diffuseTex = material.diffuseMap != null ? Assets.get(material.diffuseMap) : null;
        Texture normalTex = material.normalMap != null ? Assets.get(material.normalMap) : null;
        Texture specularTex = material.specularMap != null ? Assets.get(material.specularMap) : null;

        if (diffuseTex != null) {
            diffuseTex.bind(baseUnit);
            GL20.glUniform1i(getLocationOf(programId, "diffuse"), baseUnit);
        }
        GL20.glUniform1i(getLocationOf(programId, "has_diffuse"), diffuseTex != null ? 1 : 0);

        if (normalTex != null) {
            normalTex.bind(baseUnit + 1);
            GL20.glUniform1i(getLocationOf(programId, "normal"), baseUnit + 1);
        }
        GL20.glUniform1i(getLocationOf(programId, "has_normal"), normalTex != null ? 1 : 0);

        if (specularTex != null) {
            specularTex.bind(baseUnit + 2);
            GL20.glUniform1i(getLocationOf(programId, "specular"), baseUnit + 2);
        }
        GL20.glUniform1i(getLocationOf(programId, "has_specular"), specularTex != null ? 1 : 0);

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
