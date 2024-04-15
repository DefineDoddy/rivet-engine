package me.definedoddy.engine.manager;

import me.definedoddy.engine.rendering.basicMesh.BasicMeshRenderer;
import me.definedoddy.engine.rendering.basicMesh.BasicMeshShader;
import me.definedoddy.engine.rendering.entity.EntityRenderer;
import me.definedoddy.engine.rendering.object.mesh.Mesh;
import me.definedoddy.engine.rendering.object.mesh.MeshLoader;
import me.definedoddy.toolkit.file.Resource;

public class RenderEngine {
    private final BasicMeshRenderer basicMeshRenderer;
    //private final EntityRenderer entityRenderer;

    public RenderEngine() {
        basicMeshRenderer = new BasicMeshRenderer(BasicMeshShader.create());
        //entityRenderer = new EntityRenderer(EntityShader.create());
    }

    public void init() {
        Mesh mesh = MeshLoader.loadFromObjFile(new Resource("obj/stall.obj"), new Resource("obj/stallTexture.png"));
        /*Mesh mesh = new Mesh();
        mesh.setIndices(new int[] {
                0, 1, 3,
                3, 1, 2
        });
        mesh.setVertexPositions(new float[] {
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f
        });*/
        //mesh.setColour(Color.GREEN);
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
