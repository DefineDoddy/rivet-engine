package me.definedoddy.dreamWeavers.scene;

import me.definedoddy.dreamWeavers.models.Stall;
import me.definedoddy.engine.entity.EntityFactory;
import me.definedoddy.engine.entity.ModelEntity;
import me.definedoddy.engine.rendering.cubemap.CubeMapLoader;
import me.definedoddy.engine.rendering.lighting.Light;
import me.definedoddy.engine.rendering.object.model.Model;
import me.definedoddy.engine.rendering.object.model.ModelLoader;
import me.definedoddy.engine.rendering.skybox.Skybox;
import me.definedoddy.engine.rendering.texture.TextureLoader;
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

        addEntity(new Stall());

        // Add lighting
        Light light1 = new Light(new Vector3f(0, 3, 0), Color.RED);
        light1.setAttenuation(1, 0.01f, 0.002f);
        addLight(light1);

        Light light2 = new Light(new Vector3f(0, 2, 0), Color.GREEN.darker());
        light2.setAttenuation(1, 0.01f, 0.002f);
        addLight(light2);

        // Set skybox
        setSkybox(new Skybox(CubeMapLoader.load(
                TextureLoader.loadTextureCubeMap(new Resource("skybox/right.png")),
                TextureLoader.loadTextureCubeMap(new Resource("skybox/left.png")),
                TextureLoader.loadTextureCubeMap(new Resource("skybox/top.png")),
                TextureLoader.loadTextureCubeMap(new Resource("skybox/bottom.png")),
                TextureLoader.loadTextureCubeMap(new Resource("skybox/back.png")),
                TextureLoader.loadTextureCubeMap(new Resource("skybox/front.png"))
        )));
    }

    @Override
    public void update() {
        super.update();
        getSkybox().setRotation(getSkybox().getRotation() + 0.0005f);
    }
}
