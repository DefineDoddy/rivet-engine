package me.definedoddy.engine.rendering.object.model;

import me.definedoddy.engine.manager.GameManager;
import me.definedoddy.engine.rendering.config.RenderConfig;
import me.definedoddy.engine.rendering.model.ModelShader;
import me.definedoddy.engine.rendering.object.mesh.Mesh;
import me.definedoddy.engine.rendering.texture.Material;
import me.definedoddy.engine.utils.maths.MathsUtils;
import me.definedoddy.toolkit.memory.Disposable;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Model implements Disposable {
    private final Mesh mesh;
    private final Material material;

    public Model(Mesh mesh, Material material) {
        this.mesh = mesh;
        this.material = material;
    }

    public void render(Vector3f position, Vector3f rotation, Vector3f scale) {
        ModelShader modelShader = GameManager.getRenderEngine().getModelRenderer().getShader();
        RenderConfig renderConfig = GameManager.getRenderEngine().getRenderConfig();

        Matrix4f transformMat = MathsUtils.createTransformationMatrix(position, rotation, scale);
        modelShader.getTransformationMatrix().loadMatrix(transformMat);

        if (material != null && material.getDiffuse() != null) {
            GL20.glBindTexture(GL11.GL_TEXTURE_2D, material.getDiffuse().getId());

            if (renderConfig.useMipmapping()) {
                GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
                GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, renderConfig.getLODBias());
            }

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

    @Override
    public Model clone() {
        return new Model(mesh, material.clone());
    }
}
