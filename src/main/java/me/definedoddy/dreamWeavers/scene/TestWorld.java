package me.definedoddy.dreamWeavers.scene;

import me.definedoddy.dreamWeavers.models.Dragon;
import me.definedoddy.dreamWeavers.models.Stall;
import me.definedoddy.engine.entity.EntityFactory;
import me.definedoddy.engine.entity.ModelEntity;
import me.definedoddy.engine.physics.Time;
import me.definedoddy.engine.rendering.cubemap.CubeMapLoader;
import me.definedoddy.engine.rendering.lighting.DirectionalLight;
import me.definedoddy.engine.rendering.lighting.PointLight;
import me.definedoddy.engine.rendering.lighting.SpotLight;
import me.definedoddy.engine.rendering.skybox.Skybox;
import me.definedoddy.engine.rendering.texture.Material;
import me.definedoddy.engine.rendering.texture.MaterialBuilder;
import me.definedoddy.engine.rendering.texture.TextureLoader;
import me.definedoddy.engine.rendering.texture.TextureType;
import me.definedoddy.engine.scene.Scene;
import me.definedoddy.toolkit.file.Resource;
import org.joml.Vector3f;

import java.awt.*;

public class TestWorld extends Scene {
    @Override
    public void load() {
        // Create environment
        ModelEntity groundQuad = EntityFactory.createQuad(new Vector3f(100, 100, 0), new Material());
        groundQuad.getRotation().set(90, 180, 0);
        addEntity(groundQuad);

        Dragon dragon = new Dragon();
        dragon.getPosition().set(0, 0, -5);
        addEntity(dragon);

        addEntity(new Stall());

        ModelEntity cube = EntityFactory.createCube(new Vector3f(1, 1, 1), new MaterialBuilder()
                        .diffuse(TextureLoader.loadTexture2D(new Resource("assets/icon.png"), TextureType.DIFFUSE))
                        .shininess(0.2f)
                        .build());
        cube.getPosition().set(8, 3, 0);
        addEntity(cube);

        addLighting();

        // Set skybox
        setSkybox(new Skybox(CubeMapLoader.load(
                TextureLoader.loadTextureCubeMap(new Resource("assets/skybox/right.png")),
                TextureLoader.loadTextureCubeMap(new Resource("assets/skybox/left.png")),
                TextureLoader.loadTextureCubeMap(new Resource("assets/skybox/top.png")),
                TextureLoader.loadTextureCubeMap(new Resource("assets/skybox/bottom.png")),
                TextureLoader.loadTextureCubeMap(new Resource("assets/skybox/back.png")),
                TextureLoader.loadTextureCubeMap(new Resource("assets/skybox/front.png"))
        )));
    }

    private void addLighting() {
        DirectionalLight sun = new DirectionalLight(new Vector3f(1, -1, 0), Color.WHITE);
        sun.setIntensity(0.2f);
        addLight(sun);

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
    }

    @Override
    public void update() {
        super.update();
        getSkybox().rotate((float) Time.getDeltaTime() * 0.02f);
    }
}
