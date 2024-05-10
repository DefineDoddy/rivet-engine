package me.definedoddy.engine.rendering.model;

import me.definedoddy.engine.entity.ModelEntity;
import me.definedoddy.engine.manager.GameManager;
import me.definedoddy.engine.rendering.camera.Camera;
import me.definedoddy.engine.rendering.lighting.Light;
import me.definedoddy.engine.rendering.object.mesh.Mesh;
import me.definedoddy.engine.rendering.object.model.Model;
import me.definedoddy.engine.scene.SceneManager;
import me.definedoddy.engine.utils.maths.MathsUtils;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ModelRenderer {
    private final ModelShader shader;
    private final List<ModelEntity> entities = new ArrayList<>();

    public ModelRenderer(ModelShader shader) {
        this.shader = shader;
    }

    public void render() {
        shader.bind();

        shader.getProjectionMatrix().loadMatrix(Camera.get().getProjectionMatrix());
        shader.getViewMatrix().loadMatrix(Camera.get().getViewMatrix());

        applyLighting();

        Mesh mesh = null;

        for (ModelEntity entity : entities) {
            Model model = entity.getModel();

            if (mesh != model.getMesh()) {
                mesh = model.getMesh();
                mesh.bind();
            }

            ModelShader modelShader = GameManager.getRenderEngine().getModelRenderer().getShader();
            shader.getColour().loadColour(model.getMaterial().getTintColour());
            shader.getReflectivity().loadFloat(model.getMaterial().getReflectivity());

            Matrix4f transformMat = MathsUtils.createTransformationMatrix(entity.getPosition(), entity.getRotation(), entity.getScale());
            modelShader.getTransformationMatrix().loadMatrix(transformMat);

            model.render();
        }

        if (mesh != null) mesh.unbind();
        shader.unbind();
    }

    private void applyLighting() {
        List<Light> lights = SceneManager.getCurrentScene().getLights();
        int maxLights = GameManager.getRenderEngine().getRenderConfig().getMaxLightsOnMesh();
        shader.getNumLights().loadInt(lights.size());

        for (int i = 0; i < maxLights; i++) {
            if (i < lights.size()) {
                shader.getLightPositions().loadVec3(i, lights.get(i).getPosition());
                shader.getLightColours().loadColour(i, lights.get(i).getColour());
            } else {
                shader.getLightPositions().loadVec3(i, new Vector3f());
                shader.getLightColours().loadColour(i, Color.BLACK);
            }
        }
    }

    public ModelShader getShader() {
        return shader;
    }

    public void addEntity(ModelEntity entity) {
        entities.add(entity);
    }

    public void removeEntity(ModelEntity entity) {
        entities.remove(entity);
    }
}
