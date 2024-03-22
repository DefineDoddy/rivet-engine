package me.definedoddy.engine.manager;

import me.definedoddy.engine.rendering.basicMesh.BasicMeshRenderer;
import me.definedoddy.engine.rendering.basicMesh.BasicMeshShader;
import me.definedoddy.engine.rendering.entity.EntityRenderer;
import me.definedoddy.engine.rendering.object.Attribute;
import me.definedoddy.engine.rendering.object.Mesh;
import me.definedoddy.engine.rendering.object.Vao;
import me.definedoddy.engine.rendering.object.Vbo;
import org.lwjgl.opengl.GL11;

public class RenderEngine {
    private final BasicMeshRenderer basicMeshRenderer;
    //private final EntityRenderer entityRenderer;
    private Mesh mesh;

    public RenderEngine() {
        basicMeshRenderer = new BasicMeshRenderer(BasicMeshShader.create());
        //entityRenderer = new EntityRenderer(EntityShader.create());
    }

    public void init() {
        Vao vao = new Vao();
        // create basic triangle
        vao.storeData(Vbo.create(0, 3, new float[] {
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f
        }), new Attribute(0, 3, GL11.GL_FLOAT));
        mesh = new Mesh(vao);
        basicMeshRenderer.addMeshToRender(mesh);
    }

    public void update() {
        basicMeshRenderer.render();
        //entityRenderer.render();
    }

    public BasicMeshRenderer getBasicMeshRenderer() {
        return basicMeshRenderer;
    }

    public EntityRenderer getEntityRenderer() {
        return null;
    }
}
