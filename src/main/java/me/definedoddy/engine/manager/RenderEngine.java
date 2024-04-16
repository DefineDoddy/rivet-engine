package me.definedoddy.engine.manager;

import me.definedoddy.engine.entity.ModelEntity;
import me.definedoddy.engine.rendering.entity.EntityRenderer;
import me.definedoddy.engine.rendering.entity.EntityShader;
import me.definedoddy.engine.rendering.object.model.Model;
import me.definedoddy.engine.rendering.object.model.ModelLoader;
import me.definedoddy.toolkit.file.Resource;

public class RenderEngine {
    private final EntityRenderer entityRenderer;

    public RenderEngine() {
        entityRenderer = new EntityRenderer(EntityShader.create());
    }

    public void init() {
        Model model = ModelLoader.loadFromObjFile(new Resource("obj/stall.obj"), new Resource("obj/stallTexture.png"));
        /*Model model = new Model();
        model.setIndices(new int[] {
                0, 1, 3,
                3, 1, 2
        });
        model.setVertexPositions(new float[] {
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f
        });
        model.setTextureCoords(new float[] {
                0, 0,
                0, 1,
                1, 1,
                1, 0
        });
        model.setTexture(TextureLoader.loadTexture(new Resource("obj/stallTexture.png")));
        model.setColour(Color.GREEN);*/
        ModelEntity entity = new ModelEntity() {
            @Override
            protected Model defineModel() {
                return model;
            }
        };
        entityRenderer.addEntityToRender(entity);
    }

    public void update() {
        entityRenderer.render();
    }

    public EntityRenderer getEntityRenderer() {
        return entityRenderer;
    }
}
