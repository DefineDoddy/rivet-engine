package me.definedoddy.dreamWeavers.scene;

import me.definedoddy.dreamWeavers.models.Stall;
import me.definedoddy.engine.entity.EntityFactory;
import me.definedoddy.engine.entity.ModelEntity;
import me.definedoddy.engine.rendering.cubemap.CubeMapLoader;
import me.definedoddy.engine.rendering.lighting.PointLight;
import me.definedoddy.engine.rendering.object.model.Model;
import me.definedoddy.engine.rendering.object.model.ModelLoader;
import me.definedoddy.engine.rendering.object.model.ModelUtils;
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
                model.getMaterial().setShininess(1f);
                return model;
            }
        };
        dragon.getPosition().set(0, 0, -5);
        addEntity(dragon);

        addEntity(new Stall());


        ModelEntity cube = new ModelEntity() {
            @Override
            protected Model defineModel() {
                return ModelUtils.createCube(new Vector3f(2, 2, 2), null);
            }
        };
        cube.getPosition().set(0, 8, 0);
        addEntity(cube);

        // Add lighting
        PointLight light1 = new PointLight(new Vector3f(0, 3, 0), Color.RED);
        light1.setRadius(10f);
        addLight(light1);

        PointLight light2 = new PointLight(new Vector3f(0, 2, 0), Color.GREEN.darker());
        light2.setRadius(2f);
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
