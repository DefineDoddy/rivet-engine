package org.rivetengine.rendering.shader.uniform.custom;

import org.rivetengine.entity.components.rendering.lighting.DirectionalLight;
import org.rivetengine.entity.components.rendering.lighting.Light;
import org.rivetengine.entity.components.rendering.lighting.PointLight;
import org.rivetengine.entity.components.rendering.lighting.SpotLight;
import org.rivetengine.rendering.shader.uniform.Uniform;
import org.lwjgl.opengl.GL20;

public class UniformLights extends Uniform {
    public UniformLights(String name) {
        super(name);
    }

    public void loadLight(int index, Light light) {
        loadProperties(index, light);
        loadLightType(index, light);
    }

    private void loadLightType(int index, Light light) {
        if (light instanceof DirectionalLight) {
            GL20.glUniform1i(getLocationOf(getProgramId(), index, "type"), 0);
        } else if (light instanceof PointLight) {
            GL20.glUniform1i(getLocationOf(getProgramId(), index, "type"), 1);
        } else if (light instanceof SpotLight) {
            GL20.glUniform1i(getLocationOf(getProgramId(), index, "type"), 2);
        }
    }

    private void loadProperties(int index, Light light) {
        // Store base values
        GL20.glUniform3f(getLocationOf(getProgramId(), index, "colour"),
                light.getColour().getRed() / 255f,
                light.getColour().getGreen() / 255f,
                light.getColour().getBlue() / 255f);

        GL20.glUniform1f(getLocationOf(getProgramId(), index, "intensity"), light.getIntensity());

        // Store position
        if (light instanceof DirectionalLight directionalLight) {
            GL20.glUniform3f(getLocationOf(getProgramId(), index, "direction"),
                    directionalLight.getDirection().x,
                    directionalLight.getDirection().y,
                    directionalLight.getDirection().z);
        } else if (light instanceof PointLight pointLight) {
            GL20.glUniform3f(getLocationOf(getProgramId(), index, "position"),
                    pointLight.getPosition().x,
                    pointLight.getPosition().y,
                    pointLight.getPosition().z);

            float radius = pointLight.getRadius();
            float constant = 1f;
            float linear = 4.5f / radius;
            float quadratic = 85f / (radius * radius);

            GL20.glUniform1f(getLocationOf(getProgramId(), index, "constant"), constant);
            GL20.glUniform1f(getLocationOf(getProgramId(), index, "linear"), linear);
            GL20.glUniform1f(getLocationOf(getProgramId(), index, "quadratic"), quadratic);
        } else if (light instanceof SpotLight spotLight) {
            GL20.glUniform3f(getLocationOf(getProgramId(), index, "position"),
                    spotLight.getPosition().x,
                    spotLight.getPosition().y,
                    spotLight.getPosition().z);

            GL20.glUniform3f(getLocationOf(getProgramId(), index, "direction"),
                    spotLight.getDirection().x,
                    spotLight.getDirection().y,
                    spotLight.getDirection().z);

            float constant = 1f;
            float linear = 0.09f;
            float quadratic = 0.032f;

            GL20.glUniform1f(getLocationOf(getProgramId(), index, "constant"), constant);
            GL20.glUniform1f(getLocationOf(getProgramId(), index, "linear"), linear);
            GL20.glUniform1f(getLocationOf(getProgramId(), index, "quadratic"), quadratic);

            GL20.glUniform1f(getLocationOf(getProgramId(), index, "inner_radius"), spotLight.getInnerRadius());
            GL20.glUniform1f(getLocationOf(getProgramId(), index, "outer_radius"), spotLight.getOuterRadius());
        }
    }

    private int getLocationOf(int programID, int index, String property) {
        return GL20.glGetUniformLocation(programID, getName() + "[" + index + "]." + property);
    }
}
