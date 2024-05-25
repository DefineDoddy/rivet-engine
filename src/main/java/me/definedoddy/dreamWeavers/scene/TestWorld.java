package me.definedoddy.dreamWeavers.scene;

import me.definedoddy.dreamWeavers.models.Stall;
import me.definedoddy.engine.entity.EntityFactory;
import me.definedoddy.engine.entity.ModelEntity;
import me.definedoddy.engine.rendering.cubemap.CubeMapLoader;
import me.definedoddy.engine.rendering.lighting.DirectionalLight;
import me.definedoddy.engine.rendering.lighting.PointLight;
import me.definedoddy.engine.rendering.lighting.SpotLight;
import me.definedoddy.engine.rendering.object.model.Model;
import me.definedoddy.engine.rendering.object.model.ModelLoader;
import me.definedoddy.engine.rendering.object.model.ModelUtils;
import me.definedoddy.engine.rendering.skybox.Skybox;
import me.definedoddy.engine.rendering.texture.*;
import me.definedoddy.engine.scene.Scene;
import me.definedoddy.toolkit.file.Resource;
import org.joml.Vector3f;

import java.awt.*;

public class TestWorld extends Scene {
    @Override
    public void load() {
        // Create environment
        ModelEntity groundQuad = EntityFactory.createQuad(new Vector3f(100, 100, 0), Material.defaultMaterial());
        groundQuad.getRotation().set(90, 180, 0);
        addEntity(groundQuad);

        ModelEntity dragon = new ModelEntity() {
            @Override
            protected Model defineModel() {
                Resource obj = new Resource("obj/dragon.obj");
                Model model = ModelLoader.loadFromObjFile(obj);
                model.getMaterial().setShininess(1f);
                return model;
            }
        };
        dragon.getPosition().set(0, 0, -5);
        addEntity(dragon);

        ModelEntity box = new ModelEntity() {
            @Override
            protected Model defineModel() {
                Resource obj = new Resource("obj/box/model.obj");

                Texture diffuse = TextureLoader.loadTexture2D(new Resource("obj/box/diffuse.png"), TextureType.DIFFUSE);
                Texture normal = TextureLoader.loadTexture2D(new Resource("obj/box/normal.png"), TextureType.NORMAL);
                Texture specular = TextureLoader.loadTexture2D(new Resource("obj/box/specular.png"), TextureType.SPECULAR);
                Material material = new MaterialBuilder(diffuse).normal(normal).specular(specular).build();

                return ModelLoader.loadFromObjFile(obj, material);
            }
        };
        box.getPosition().set(0, 0, 10);
        addEntity(box);

        addEntity(new Stall());


        ModelEntity cube = new ModelEntity() {
            @Override
            protected Model defineModel() {
                return ModelUtils.createCube(new Vector3f(2, 2, 2), Material.defaultMaterial());
            }
        };
        cube.getPosition().set(0, 8, 0);
        addEntity(cube);

        DirectionalLight sun = new DirectionalLight(new Vector3f(1, -1, 0), Color.DARK_GRAY.darker());
        addLight(sun);

        // Add lighting
        PointLight light1 = new PointLight(new Vector3f(0, 3, 0), Color.WHITE.darker());
        light1.setRadius(100f);
        addLight(light1);

        PointLight light2 = new PointLight(new Vector3f(0, 3, 5), Color.GREEN.darker());
        light2.setRadius(20f);
        addLight(light2);

        SpotLight light3 = new SpotLight(new Vector3f(0, 3, -10), new Vector3f(1, -1, 0), Color.RED.darker());
        light3.setInnerRadius(15f);
        light3.setOuterRadius(30f);
        addLight(light3);

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
