package org.rivetengine.rendering.shader.uniform.custom;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.rivetengine.entity.Entity;
import org.rivetengine.entity.components.rendering.lighting.DirectionalLight;
import org.rivetengine.entity.components.rendering.lighting.Light;
import org.rivetengine.entity.components.rendering.lighting.PointLight;
import org.rivetengine.entity.components.rendering.lighting.SpotLight;
import org.rivetengine.rendering.RenderUtils;
import org.rivetengine.rendering.shader.uniform.Uniform;
import org.lwjgl.opengl.GL20;

public class UniformLights extends Uniform {
    public UniformLights(String name) {
        super(name);
    }

    public void loadLight(int index, Entity lightEntity) {
        loadProperties(index, lightEntity);
        loadLightType(index, lightEntity);
    }

    private void loadLightType(int index, Entity lightEntity) {
        if (lightEntity.hasComponent(DirectionalLight.class)) {
            GL20.glUniform1i(getLocationOf(programId, index, "type"), 0);
        } else if (lightEntity.hasComponent(PointLight.class)) {
            GL20.glUniform1i(getLocationOf(programId, index, "type"), 1);
        } else if (lightEntity.hasComponent(SpotLight.class)) {
            GL20.glUniform1i(getLocationOf(programId, index, "type"), 2);
        }
    }

    private void loadProperties(int index, Entity lightEntity) {
        Light lightComponent = lightEntity.getComponent(Light.class);

        // Store base values
        GL20.glUniform3f(getLocationOf(programId, index, "colour"),
                lightComponent.colour.getRed() / 255f,
                lightComponent.colour.getGreen() / 255f,
                lightComponent.colour.getBlue() / 255f);

        GL20.glUniform1f(getLocationOf(programId, index, "intensity"), lightComponent.intensity);

        Matrix4f worldMatrix = RenderUtils.getWorldMatrixSafe(lightEntity);

        // Store position
        if (lightEntity.hasComponent(DirectionalLight.class)) {
            // Calculate direction the light is shining towards
            Vector3f lightDirection = new Vector3f(0, 0, -1);
            worldMatrix.transformDirection(lightDirection);

            GL20.glUniform3f(getLocationOf(programId, index, "direction"),
                    lightDirection.x,
                    lightDirection.y,
                    lightDirection.z);
        } else if (lightEntity.hasComponent(PointLight.class)) {
            PointLight pointLight = lightEntity.getComponent(PointLight.class);
            Vector3f worldPos = new Vector3f();
            worldMatrix.getTranslation(worldPos);

            GL20.glUniform3f(getLocationOf(programId, index, "position"),
                    worldPos.x,
                    worldPos.y,
                    worldPos.z);

            float radius = pointLight.radius;
            float constant = 1f;
            float linear = 4.5f / radius;
            float quadratic = 85f / (radius * radius);

            GL20.glUniform1f(getLocationOf(programId, index, "constant"), constant);
            GL20.glUniform1f(getLocationOf(programId, index, "linear"), linear);
            GL20.glUniform1f(getLocationOf(programId, index, "quadratic"), quadratic);
        } else if (lightEntity.hasComponent(SpotLight.class)) {
            SpotLight spotLight = lightEntity.getComponent(SpotLight.class);
            Vector3f worldPos = new Vector3f();
            worldMatrix.getTranslation(worldPos);

            GL20.glUniform3f(getLocationOf(programId, index, "position"),
                    worldPos.x,
                    worldPos.y,
                    worldPos.z);

            // Calculate direction the spot is pointing
            Vector3f spotDirection = new Vector3f(0, 0, -1);
            worldMatrix.transformDirection(spotDirection);

            GL20.glUniform3f(getLocationOf(programId, index, "direction"),
                    spotDirection.x,
                    spotDirection.y,
                    spotDirection.z);

            float constant = 1f;
            float linear = 0.09f;
            float quadratic = 0.032f;

            GL20.glUniform1f(getLocationOf(programId, index, "constant"), constant);
            GL20.glUniform1f(getLocationOf(programId, index, "linear"), linear);
            GL20.glUniform1f(getLocationOf(programId, index, "quadratic"), quadratic);

            GL20.glUniform1f(getLocationOf(programId, index, "inner_radius"), spotLight.innerRadius);
            GL20.glUniform1f(getLocationOf(programId, index, "outer_radius"), spotLight.outerRadius);
        }
    }

    private int getLocationOf(int programID, int index, String property) {
        return GL20.glGetUniformLocation(programID, name + "[" + index + "]." + property);
    }
}
