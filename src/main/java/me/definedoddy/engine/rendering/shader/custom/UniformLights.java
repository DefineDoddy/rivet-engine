package me.definedoddy.engine.rendering.shader.custom;

import me.definedoddy.engine.rendering.lighting.DirectionalLight;
import me.definedoddy.engine.rendering.lighting.Light;
import me.definedoddy.engine.rendering.lighting.PointLight;
import me.definedoddy.engine.rendering.lighting.SpotLight;
import me.definedoddy.engine.rendering.shader.Uniform;
import org.lwjgl.opengl.GL20;

public class UniformLights extends Uniform {
    public UniformLights(String name) {
        super(name);
    }

    public void loadLight(int index, Light light) {
        loadColours(index, light);
        loadProperties(index, light);
        loadLightType(index, light);
    }

    private void loadLightType(int index, Light light) {
        if (light instanceof DirectionalLight) {
            GL20.glUniform1i(getLocationOf(getProgramID(), index, "type"), 0);
        } else if (light instanceof PointLight) {
            GL20.glUniform1i(getLocationOf(getProgramID(), index, "type"), 1);
        } else if (light instanceof SpotLight) {
            GL20.glUniform1i(getLocationOf(getProgramID(), index, "type"), 2);
        }
    }

    private void loadProperties(int index, Light light) {
        // Store the position
        if (light instanceof DirectionalLight directionalLight) {
            GL20.glUniform3f(getLocationOf(getProgramID(), index, "direction"),
                    directionalLight.getDirection().x,
                    directionalLight.getDirection().y,
                    directionalLight.getDirection().z
            );
        } else if (light instanceof PointLight pointLight) {
            GL20.glUniform3f(getLocationOf(getProgramID(), index, "position"),
                    pointLight.getPosition().x,
                    pointLight.getPosition().y,
                    pointLight.getPosition().z
            );

            float radius = pointLight.getRadius();
            float constant = 1f;
            float linear = 4.5f / radius;
            float quadratic = 75f / (radius * radius);

            GL20.glUniform1f(getLocationOf(getProgramID(), index, "constant"), constant);
            GL20.glUniform1f(getLocationOf(getProgramID(), index, "linear"), linear);
            GL20.glUniform1f(getLocationOf(getProgramID(), index, "quadratic"), quadratic);
        } else if (light instanceof SpotLight spotLight) {
            GL20.glUniform3f(getLocationOf(getProgramID(), index, "position"),
                    spotLight.getPosition().x,
                    spotLight.getPosition().y,
                    spotLight.getPosition().z
            );

            GL20.glUniform3f(getLocationOf(getProgramID(), index, "direction"),
                    spotLight.getDirection().x,
                    spotLight.getDirection().y,
                    spotLight.getDirection().z
            );

            float constant = 1f;
            float linear = 0.09f;
            float quadratic = 0.032f;

            GL20.glUniform1f(getLocationOf(getProgramID(), index, "constant"), constant);
            GL20.glUniform1f(getLocationOf(getProgramID(), index, "linear"), linear);
            GL20.glUniform1f(getLocationOf(getProgramID(), index, "quadratic"), quadratic);

            GL20.glUniform1f(getLocationOf(getProgramID(), index, "inner_radius"), spotLight.getInnerRadius());
            GL20.glUniform1f(getLocationOf(getProgramID(), index, "outer_radius"), spotLight.getOuterRadius());
        }
    }

    private void loadColours(int index, Light light) {
        // Store the ambient color
        GL20.glUniform3f(getLocationOf(getProgramID(), index, "ambient"),
                light.getAmbientColour().getRed() / 255f,
                light.getAmbientColour().getGreen() / 255f,
                light.getAmbientColour().getBlue() / 255f
        );

        // Store the diffuse color
        GL20.glUniform3f(getLocationOf(getProgramID(), index, "diffuse"),
                light.getDiffuseColour().getRed() / 255f,
                light.getDiffuseColour().getGreen() / 255f,
                light.getDiffuseColour().getBlue() / 255f
        );

        // Store the specular color
        GL20.glUniform3f(getLocationOf(getProgramID(), index, "specular"),
                light.getSpecularColour().getRed() / 255f,
                light.getSpecularColour().getGreen() / 255f,
                light.getSpecularColour().getBlue() / 255f
        );
    }

    private int getLocationOf(int programID, int index, String property) {
        return GL20.glGetUniformLocation(programID, getName() + "[" + index + "]." + property);
    }
}
