package me.definedoddy.engine.manager;

import me.definedoddy.engine.entity.ModelEntity;
import me.definedoddy.engine.rendering.entity.EntityRenderer;
import me.definedoddy.engine.rendering.entity.EntityShader;
import me.definedoddy.engine.rendering.object.model.Model;
import me.definedoddy.engine.rendering.object.model.ModelLoader;
import me.definedoddy.engine.rendering.object.model.ModelUtils;
import me.definedoddy.toolkit.file.Resource;
import org.joml.Vector3f;

public class RenderEngine {
    private final EntityRenderer entityRenderer;

    public RenderEngine() {
        entityRenderer = new EntityRenderer(EntityShader.create());
    }

    public void init() {
        ModelEntity stall = new ModelEntity() {
            @Override
            protected Model defineModel() {
                return ModelLoader.loadFromObjFile(new Resource("obj/stall.obj"), new Resource("obj/stallTexture.png"));
            }
        };
        entityRenderer.addEntityToRender(stall);

        ModelEntity groundQuad = new ModelEntity() {
            @Override
            protected Model defineModel() {
                return ModelUtils.createQuad(new Vector3f(100, 100, 0), null);
            }
        };
        groundQuad.getRotation().set(90, 0, 0);
        entityRenderer.addEntityToRender(groundQuad);
    }

    public void update() {
        entityRenderer.render();
    }

    public EntityRenderer getEntityRenderer() {
        return entityRenderer;
    }
}
