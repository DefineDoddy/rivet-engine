package me.definedoddy.engine.manager;

import me.definedoddy.engine.rendering.basicMesh.BasicMeshRenderer;
import me.definedoddy.engine.rendering.basicMesh.BasicMeshShader;
import me.definedoddy.engine.rendering.entity.EntityRenderer;
import me.definedoddy.engine.rendering.object.Mesh;

import java.awt.*;

public class RenderEngine {
    private final BasicMeshRenderer basicMeshRenderer;
    //private final EntityRenderer entityRenderer;

    public RenderEngine() {
        basicMeshRenderer = new BasicMeshRenderer(BasicMeshShader.create());
        //entityRenderer = new EntityRenderer(EntityShader.create());
    }

    public void init() {
        Mesh mesh = new Mesh();
        mesh.setVertexPositions(new float[] {
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,

                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f,
                -0.5f, 0.5f, 0f
        });
        mesh.setColour(Color.GREEN);
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
