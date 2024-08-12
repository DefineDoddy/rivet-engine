package me.definedoddy.engine.rendering.object.model;

import me.definedoddy.engine.manager.GameManager;
import me.definedoddy.engine.rendering.object.mesh.Mesh;
import me.definedoddy.engine.rendering.shader.model.ModelShader;
import me.definedoddy.engine.rendering.texture.Material;
import me.definedoddy.engine.utils.maths.MathsUtils;
import me.definedoddy.toolkit.debug.DebugHandler;
import me.definedoddy.toolkit.memory.Disposable;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import java.util.Map;

public class Model implements Disposable {
    private final Map<Mesh, Material> meshData;
    private final Material material;

    private DebugHandler debugHandler;
    private boolean renderNormals;
    private boolean wireframe;

    public Model(@NotNull Map<Mesh, Material> meshData, @NotNull Material material) {
        this.meshData = meshData;
        this.material = material;
        initDebugHandler();
    }

    private void initDebugHandler() {
        debugHandler = new DebugHandler();
        debugHandler.createRenderNormalsCallback(visible -> renderNormals = visible);
        debugHandler.createWireframeCallback(visible -> wireframe = visible);
    }

    public void render(Vector3f position, Vector3f rotation, Vector3f scale) {
        ModelShader modelShader = GameManager.getRenderEngine().getModelRenderer().getShader();

        Matrix4f transformMat = MathsUtils.createTransformationMatrix(position, rotation, scale);
        modelShader.getTransformMatrix().loadMatrix(transformMat);

        //material.bind();
        if (wireframe) GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);

        for (Map.Entry<Mesh, Material> entry : meshData.entrySet()) {
            Mesh mesh = entry.getKey();
            Material material = entry.getValue();

            modelShader.getMaterial().loadMaterial(material);
            material.bind();

            mesh.bind();
            mesh.render();
            mesh.unbind();

            material.unbind();
        }

        //material.unbind();
        if (wireframe) GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);

        //if (renderNormals) renderNormals(position, rotation, scale);
    }

    public Material getMaterial() {
        return material;
    }

    @Override
    public void dispose() {
        for (Mesh mesh : meshData.keySet()) {
            mesh.dispose();
        }
        material.dispose();
        debugHandler.dispose();
    }
}
