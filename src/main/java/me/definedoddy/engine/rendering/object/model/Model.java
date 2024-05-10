package me.definedoddy.engine.rendering.object.model;

import me.definedoddy.engine.manager.GameManager;
import me.definedoddy.engine.rendering.model.ModelShader;
import me.definedoddy.engine.rendering.object.mesh.Mesh;
import me.definedoddy.engine.rendering.texture.Material;
import me.definedoddy.toolkit.memory.Disposable;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class Model implements Disposable {
    private final Mesh mesh;
    private final Material material;

    public Model(Mesh mesh, Material material) {
        this.mesh = mesh;
        this.material = material;
    }

    public void render() {
        ModelShader modelShader = GameManager.getRenderEngine().getModelRenderer().getShader();

        if (material != null && material.getDiffuse() != null) {
            GL20.glBindTexture(GL11.GL_TEXTURE_2D, material.getDiffuse().getId());
            modelShader.getUseTexture().loadBoolean(true);

        } else {
            GL20.glBindTexture(GL11.GL_TEXTURE_2D, 0);
            modelShader.getUseTexture().loadBoolean(false);
        }

        mesh.render();
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
}
