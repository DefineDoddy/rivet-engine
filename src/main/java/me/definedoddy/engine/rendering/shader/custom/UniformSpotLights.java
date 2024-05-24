package me.definedoddy.engine.rendering.shader.custom;

import me.definedoddy.engine.rendering.lighting.SpotLight;
import me.definedoddy.engine.rendering.shader.Uniform;
import org.lwjgl.opengl.GL20;

public class UniformSpotLights extends Uniform {
    public UniformSpotLights(String name) {
        super(name);
    }

    public void loadSpotLight(SpotLight light, int index) {
        // Load default light properties
        GL20.glUniform3f(getLocationOf(getProgramID(), index, "diffuse_colour"),
                light.getDiffuseColour().getRed() / 255f,
                light.getDiffuseColour().getGreen() / 255f,
                light.getDiffuseColour().getBlue() / 255f
        );

        GL20.glUniform3f(getLocationOf(getProgramID(), index, "ambient_colour"),
                light.getAmbientColour().getRed() / 255f,
                light.getAmbientColour().getGreen() / 255f,
                light.getAmbientColour().getBlue() / 255f
        );

        GL20.glUniform3f(getLocationOf(getProgramID(), index, "specular_colour"),
                light.getSpecularColour().getRed() / 255f,
                light.getSpecularColour().getGreen() / 255f,
                light.getSpecularColour().getBlue() / 255f
        );

        // Load spot light properties
        GL20.glUniform3f(getLocationOf(getProgramID(), index, "position"),
                light.getPosition().x,
                light.getPosition().y,
                light.getPosition().z
        );

        GL20.glUniform3f(getLocationOf(getProgramID(), index, "direction"),
                light.getDirection().x,
                light.getDirection().y,
                light.getDirection().z
        );

        float constant = 1f;
        float linear = 0.09f;
        float quadratic = 0.032f;

        GL20.glUniform1f(getLocationOf(getProgramID(), index, "constant"), constant);
        GL20.glUniform1f(getLocationOf(getProgramID(), index, "linear"), linear);
        GL20.glUniform1f(getLocationOf(getProgramID(), index, "quadratic"), quadratic);

        GL20.glUniform1f(getLocationOf(getProgramID(), index, "inner_radius"), light.getInnerRadius());
        GL20.glUniform1f(getLocationOf(getProgramID(), index, "outer_radius"), light.getOuterRadius());
    }

    private int getLocationOf(int programID, int index, String property) {
        return GL20.glGetUniformLocation(programID, getName() + "[" + index + "]." + property);
    }
}
