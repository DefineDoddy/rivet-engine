package org.rivetengine.rendering.mesh;

import org.rivetengine.entity.Entity;
import org.rivetengine.entity.components.rendering.Material;
import org.rivetengine.entity.components.rendering.Mesh3d;
import org.rivetengine.entity.components.rendering.lighting.Light;
import org.rivetengine.core.Assets;
import org.rivetengine.core.Game;
import org.rivetengine.rendering.RenderUtils;
import org.rivetengine.rendering.Rendering;
import org.rivetengine.rendering.mesh.shader.MeshShader;
import org.rivetengine.system.SystemUtils;
import org.rivetengine.toolkit.memory.Disposable;
import org.rivetengine.toolkit.memory.Handle;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeshRenderer implements Disposable {
    public final MeshShader shader;

    public MeshRenderer(MeshShader shader) {
        this.shader = shader;
    }

    public void render(Game game) {
        shader.bind();
        applyLighting(game);

        Matrix4f[] cameraMatrices = RenderUtils.createCameraMatrices(game);
        shader.projectionMatrix.loadMatrix(cameraMatrices[0]);
        shader.viewMatrix.loadMatrix(cameraMatrices[1]);

        List<Entity> meshEntities = SystemUtils.getEntitiesWithComponent(game, Mesh3d.class);
        Map<Handle<Mesh>, List<Entity>> batchedEntities = batchEntities(meshEntities);

        for (Map.Entry<Handle<Mesh>, List<Entity>> entry : batchedEntities.entrySet()) {
            Handle<Mesh> handle = entry.getKey();
            if (handle == null) {
                continue;
            }

            Mesh mesh = Assets.get(handle);
            if (mesh == null) {
                continue;
            }

            mesh.bind();

            // Load global mesh material
            shader.meshMaterial.loadMaterial(new Material());

            for (Entity entity : entry.getValue()) {
                Matrix4f transformMatrix = RenderUtils.getWorldMatrixSafe(entity);
                shader.transformMatrix.loadMatrix(transformMatrix);

                // Load model-specific material
                Material mat = findMaterial(entity);
                shader.modelMaterial.loadMaterial(mat);
                mesh.render();
            }

            mesh.unbind();
            enableCulling();
        }

        shader.unbind();
    }

    private Map<Handle<Mesh>, List<Entity>> batchEntities(List<Entity> meshEntities) {
        Map<Handle<Mesh>, List<Entity>> batches = new HashMap<>();

        for (Entity entity : meshEntities) {
            Mesh3d meshComponent = entity.getComponent(Mesh3d.class);
            Handle<Mesh> handle = meshComponent.mesh;

            if (handle == null) {
                continue;
            }

            batches.putIfAbsent(handle, new ArrayList<>());
            batches.get(handle).add(entity);
        }

        return batches;
    }

    private Material findMaterial(Entity entity) {
        Entity current = entity;
        while (current != null) {
            Material mat = current.getComponent(Material.class);
            if (mat != null) {
                return mat;
            }
            current = current.getParent();
        }
        return new Material(); // default
    }

    public void applyLighting(Game game) {
        List<Entity> lightEntities = SystemUtils.getEntitiesWithComponent(game, Light.class);
        int maxLights = Rendering.MAX_LIGHTS;

        if (lightEntities.size() > maxLights) {
            lightEntities = lightEntities.subList(0, maxLights);
        }

        for (int i = 0; i < lightEntities.size(); i++) {
            Entity lightEntity = lightEntities.get(i);
            shader.lights.loadLight(i, lightEntity);
        }

        shader.lightCount.loadInt(lightEntities.size());
    }

    private void enableCulling() {
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
    }

    // private void disableCulling() {
    // GL11.glDisable(GL11.GL_CULL_FACE);
    // }

    @Override
    public void dispose() {
        shader.dispose();
    }
}
