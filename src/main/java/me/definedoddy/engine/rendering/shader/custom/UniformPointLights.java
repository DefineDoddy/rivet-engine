package me.definedoddy.engine.rendering.shader.custom;

import me.definedoddy.engine.rendering.lighting.PointLight;
import me.definedoddy.engine.rendering.shader.Uniform;
import org.lwjgl.opengl.GL20;

public class UniformPointLights extends Uniform {
    public UniformPointLights(String name) {
        super(name);
    }

    public void loadPointLight(PointLight light, int index) {
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

        // Load point light properties
        GL20.glUniform3f(getLocationOf(getProgramID(), index, "position"),
                light.getPosition().x,
                light.getPosition().y,
                light.getPosition().z
        );

        float radius = light.getRadius();
        float constant = 1f;
        float linear = 4.5f / radius;
        float quadratic = 75f / (radius * radius);

        GL20.glUniform1f(getLocationOf(getProgramID(), index, "constant"), constant);
        GL20.glUniform1f(getLocationOf(getProgramID(), index, "linear"), linear);
        GL20.glUniform1f(getLocationOf(getProgramID(), index, "quadratic"), quadratic);
    }

    private int getLocationOf(int programID, int index, String property) {
        return GL20.glGetUniformLocation(programID, getName() + "[" + index + "]." + property);
    }
}
