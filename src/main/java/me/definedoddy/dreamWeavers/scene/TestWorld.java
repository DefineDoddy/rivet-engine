package me.definedoddy.dreamWeavers.scene;

import me.definedoddy.dreamWeavers.models.Dragon;
import me.definedoddy.dreamWeavers.models.Stall;
import me.definedoddy.engine.context.Time;
import me.definedoddy.engine.entity.EntityFactory;
import me.definedoddy.engine.entity.ModelEntity;
import me.definedoddy.engine.input.KeyCode;
import me.definedoddy.engine.input.Keyboard;
import me.definedoddy.engine.physics.collision.BoxCollider;
import me.definedoddy.engine.physics.simulation.Rigidbody;
import me.definedoddy.engine.rendering.cubemap.CubeMapLoader;
import me.definedoddy.engine.rendering.lighting.DirectionalLight;
import me.definedoddy.engine.rendering.lighting.PointLight;
import me.definedoddy.engine.rendering.lighting.SpotLight;
import me.definedoddy.engine.rendering.skybox.Skybox;
import me.definedoddy.engine.rendering.texture.TextureLoader;
import me.definedoddy.engine.scene.Scene;
import me.definedoddy.toolkit.file.Resource;
import org.joml.Vector3f;

import java.awt.*;

public class TestWorld extends Scene {
    @Override
    public void load() {
        ModelEntity ground = EntityFactory.createQuad(new Vector3f(100, 100, 0));
        ground.addComponent(new BoxCollider());
        addEntity(ground);

        addEntity(new Stall());
        addEntity(new Dragon(new Vector3f(0, 0, -10)));

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
                TextureLoader.loadTextureCubeMap(new Resource("assets/skybox/front.png"))
        )));
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

        if (Keyboard.get().wasKeyPressed(KeyCode.R)) {
            Dragon dragon = new Dragon(new Vector3f(8, 20, 0));
            dragon.addComponent(new Rigidbody());
            addEntity(dragon);
        }
    }
}
