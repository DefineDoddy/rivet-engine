package me.definedoddy.game.scene;

import me.definedoddy.engine.core.Scene;
import me.definedoddy.engine.core.Time;
import me.definedoddy.engine.entity.EntityFactory;
import me.definedoddy.engine.entity.ModelEntity;
import me.definedoddy.engine.physics.collision.BoxCollider;
import me.definedoddy.engine.physics.simulation.KinematicBody;
import me.definedoddy.engine.rendering.cubemap.CubeMapLoader;
import me.definedoddy.engine.rendering.lighting.DirectionalLight;
import me.definedoddy.engine.rendering.lighting.PointLight;
import me.definedoddy.engine.rendering.lighting.SpotLight;
import me.definedoddy.engine.rendering.skybox.Skybox;
import me.definedoddy.engine.rendering.texture.TextureLoader;
import me.definedoddy.toolkit.file.Resource;
import org.joml.Vector3f;

import java.awt.*;

public class TestWorld extends Scene {
    @Override
    public void load() {
        ModelEntity ground = EntityFactory.createQuad(new Vector3f(100, 100, 0));
        ground.addComponent(new BoxCollider());
        ground.addComponent(new KinematicBody());
        ground.addTag("ground");
        addEntity(ground);

        addLighting();
        loadSkybox();
    }

    private void loadSkybox() {
        getEnvironment().setSkybox(new Skybox(CubeMapLoader.load(
                TextureLoader.loadTextureCubeMap(new Resource("assets/skybox/right.png")),
                TextureLoader.loadTextureCubeMap(new Resource("assets/skybox/left.png")),
                TextureLoader.loadTextureCubeMap(new Resource("assets/skybox/top.png")),
                TextureLoader.loadTextureCubeMap(new Resource("assets/skybox/bottom.png")),
                TextureLoader.loadTextureCubeMap(new Resource("assets/skybox/back.png")),
                TextureLoader.loadTextureCubeMap(new Resource("assets/skybox/front.png")))));
    }

    private void addLighting() {
        DirectionalLight sun = new DirectionalLight(new Vector3f(1, -1, 0), Color.WHITE);
        sun.setIntensity(0.5f);
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
        getEnvironment().getSkybox().rotate((float) Time.getDeltaTime() * 0.02f);
    }
}
