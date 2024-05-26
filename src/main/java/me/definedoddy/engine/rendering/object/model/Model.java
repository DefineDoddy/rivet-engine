package me.definedoddy.engine.rendering.object.model;

import me.definedoddy.engine.manager.GameManager;
import me.definedoddy.engine.rendering.model.ModelShader;
import me.definedoddy.engine.rendering.object.mesh.Mesh;
import me.definedoddy.engine.rendering.texture.Material;
import me.definedoddy.engine.utils.maths.MathsUtils;
import me.definedoddy.toolkit.memory.Disposable;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Model implements Disposable {
    private final Mesh mesh;
    private Material material;

    public Model(Mesh mesh, Material material) {
        this.mesh = mesh;
        this.material = material;
    }

    public void render(Vector3f position, Vector3f rotation, Vector3f scale) {
        if (material == null) {
            material = Material.defaultMaterial();
            throw new IllegalStateException("Material is null, applied default material.");
        }

        ModelShader modelShader = GameManager.getRenderEngine().getModelRenderer().getShader();

        Matrix4f transformMat = MathsUtils.createTransformationMatrix(position, rotation, scale);
        modelShader.getTransformMatrix().loadMatrix(transformMat);

        material.bind();

        modelShader.getMaterial().loadMaterial(material);
        mesh.render();

        material.unbind();
    }

    public Mesh getMesh() {
        return mesh;
    }

    public Material getMaterial() {
        return material;
    }

    @Override
    public void dispose() {
        mesh.dispose();
        material.dispose();
    }

    @Override
    public Model clone() {
        return new Model(mesh, material.clone());
    }
}
