package me.definedoddy.dreamWeavers.scene;

import me.definedoddy.dreamWeavers.models.Stall;
import me.definedoddy.engine.entity.EntityFactory;
import me.definedoddy.engine.entity.ModelEntity;
import me.definedoddy.engine.rendering.lighting.Light;
import me.definedoddy.engine.rendering.object.model.Model;
import me.definedoddy.engine.rendering.object.model.ModelLoader;
import me.definedoddy.engine.scene.Scene;
import me.definedoddy.toolkit.file.Resource;
import org.joml.Vector3f;

import java.awt.*;

public class TestWorld extends Scene {
    @Override
    public void load() {
        // Create environment
        ModelEntity groundQuad = EntityFactory.createQuad(new Vector3f(100, 100, 0), null);
        groundQuad.getRotation().set(90, 180, 0);
        addEntity(groundQuad);

        ModelEntity dragon = new ModelEntity() {
            @Override
            protected Model defineModel() {
                Resource obj = new Resource("obj/dragon.obj");
                Model model = ModelLoader.loadFromObjFile(obj, null);
                model.getMaterial().setReflectivity(1f);
                return model;
            }
        };
        dragon.getPosition().set(0, 0, -5);
        addEntity(dragon);

        Stall stall = new Stall();
        stall.getPosition().set(50, 5, 0);
        addEntity(stall);

        Light skyLight = new Light(new Vector3f(), Color.GREEN);
        addLight(skyLight);

        Light light1 = new Light(new Vector3f(0, 5, 0), Color.RED);
        light1.setAttenuation(1, 0.01f, 0.002f);
        addLight(light1);
    }
}
