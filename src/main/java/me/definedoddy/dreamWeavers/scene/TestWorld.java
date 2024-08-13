package me.definedoddy.dreamWeavers.scene;

import me.definedoddy.engine.entity.EntityFactory;
import me.definedoddy.engine.entity.ModelEntity;
import me.definedoddy.engine.physics.Time;
import me.definedoddy.engine.rendering.cubemap.CubeMapLoader;
import me.definedoddy.engine.rendering.lighting.DirectionalLight;
import me.definedoddy.engine.rendering.lighting.PointLight;
import me.definedoddy.engine.rendering.lighting.SpotLight;
import me.definedoddy.engine.rendering.object.model.Model;
import me.definedoddy.engine.rendering.object.model.ModelRegistry;
import me.definedoddy.engine.rendering.skybox.Skybox;
import me.definedoddy.engine.rendering.texture.*;
import me.definedoddy.engine.scene.Scene;
import me.definedoddy.toolkit.file.Resource;
import me.definedoddy.toolkit.model.obj.importer.ModelImporter;
import org.joml.Vector3f;

import java.awt.*;

public class TestWorld extends Scene {
    @Override
    public void load() {
        // Create environment
        ModelEntity groundQuad = EntityFactory.createQuad(new Vector3f(100, 100, 0), new Material());
        groundQuad.getRotation().set(90, 180, 0);
        addEntity(groundQuad);

        ModelEntity dragon = new ModelEntity() {
            @Override
            protected Model defineModel() {
                Resource obj = new Resource("assets/obj/dragon.obj");
                Model model = ModelImporter.importModel(obj);
                model.getMaterial().setShininess(1f);
                //model.getMaterial().setColour(Color.BLUE);
                model.getMeshData().values().forEach(mat -> mat.setColour(Color.BLUE));
                return model;
            }
        };
        dragon.getPosition().set(0, 0, -5);
        addEntity(dragon);

        ModelEntity box = new ModelEntity() {
            @Override
            protected Model defineModel() {
                Resource obj = new Resource("assets/obj/box/model.obj");
                return ModelImporter.importModel(obj);
            }
        };
        box.getPosition().set(0, 0, 10);
        addEntity(box);

        //ModelRegistry.register("trimm", ModelImporter.importModel(new Resource("assets/obj/trimm/Trimm_Textures_Map.obj")));
        //ModelRegistry.register("shuttle", ModelImporter.importModel(new Resource("assets/obj/shuttle/shuttle.obj")));

        ModelEntity stall = new ModelEntity() {
            @Override
            protected Model defineModel() {
                return ModelRegistry.getModel("stall");
            }
        };

        addEntity(stall);


        ModelEntity cube = EntityFactory.createCube(new Vector3f(1, 1, 1), new MaterialBuilder()
                        .diffuse(TextureLoader.loadTexture2D(new Resource("assets/icon.png"), TextureType.DIFFUSE))
                        .shininess(0.2f)
                        .build());
        cube.getPosition().set(8, 3, 0);
        addEntity(cube);

        DirectionalLight sun = new DirectionalLight(new Vector3f(1, -1, 0), Color.WHITE);
        sun.setIntensity(0.2f);
        addLight(sun);

        // Add lighting
        PointLight light1 = new PointLight(new Vector3f(0, 3, 0), Color.WHITE);
        light1.setIntensity(0.5f);
        light1.setRadius(100f);
        addLight(light1);

        PointLight light2 = new PointLight(new Vector3f(0, 3, 5), Color.GREEN);
        light2.setRadius(20f);
        addLight(light2);

        SpotLight light3 = new SpotLight(new Vector3f(8, 6, 0), new Vector3f(0, -1, 0), Color.RED);
        light3.setInnerRadius(15f);
        light3.setOuterRadius(30f);
        addLight(light3);

        // Set skybox
        setSkybox(new Skybox(CubeMapLoader.load(
                TextureLoader.loadTextureCubeMap(new Resource("assets/skybox/right.png")),
                TextureLoader.loadTextureCubeMap(new Resource("assets/skybox/left.png")),
                TextureLoader.loadTextureCubeMap(new Resource("assets/skybox/top.png")),
                TextureLoader.loadTextureCubeMap(new Resource("assets/skybox/bottom.png")),
                TextureLoader.loadTextureCubeMap(new Resource("assets/skybox/back.png")),
                TextureLoader.loadTextureCubeMap(new Resource("assets/skybox/front.png"))
        )));

        //Debug.setRenderNormals(true);
    }

    @Override
    public void update() {
        super.update();
        getSkybox().rotate((float) Time.getDeltaTime() * 0.02f);
    }
}
