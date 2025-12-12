package me.definedoddy.game.scene;

import org.rivetengine.core.Assets;
import org.rivetengine.core.Scene;
import org.rivetengine.entity.Entity;
import org.rivetengine.entity.components.Tags;
import org.rivetengine.entity.components.Transform;
import org.rivetengine.entity.components.physics.body.KinematicBody;
import org.rivetengine.entity.components.physics.collision.BoxCollider;
import org.rivetengine.entity.components.rendering.Camera;
import org.rivetengine.entity.components.rendering.Mesh3d;
import org.rivetengine.entity.components.rendering.Skybox;
import org.rivetengine.entity.components.rendering.lighting.DirectionalLight;
import org.rivetengine.entity.components.rendering.lighting.PointLight;
import org.rivetengine.entity.components.rendering.lighting.SpotLight;
import org.rivetengine.rendering.cubemap.CubeMap;
import org.rivetengine.rendering.mesh.Mesh;
import org.rivetengine.rendering.primitives.Primitives;
import org.rivetengine.toolkit.memory.Handle;

import org.joml.Vector2f;
import org.joml.Vector3f;

import java.awt.*;

public class TestWorld extends Scene {
    @Override
    public void load() {
        // Add ground
        Entity groundEntity = new Entity();

        Handle<Mesh> groundMesh = Primitives.quad(new Vector2f(100, 100));
        groundEntity.addComponent(new Mesh3d(groundMesh));

        groundEntity.addComponent(new Tags("ground"));
        groundEntity.addComponent(new BoxCollider());
        groundEntity.addComponent(new KinematicBody());

        spawn(groundEntity);

        // Add camera
        Entity mainCamera = new Entity();
        mainCamera.addComponent(new Camera());

        Handle<CubeMap> sky = Assets.load("assets/skybox", CubeMap.class);
        mainCamera.addComponent(new Skybox(sky));

        spawn(mainCamera);

        addLighting();
    }

    private void addLighting() {
        Entity sunEntity = new Entity();
        sunEntity.addComponent(new DirectionalLight(Color.WHITE, 0.5f));
        sunEntity.addComponent(new Transform(new Vector3f(), new Vector3f(-45, 0, 0), new Vector3f(1f)));
        spawn(sunEntity);

        Entity pointLightEntity = new Entity();
        pointLightEntity.addComponent(new PointLight(Color.WHITE, 0.5f));
        pointLightEntity.addComponent(new Transform(new Vector3f(0, 5, 0), new Vector3f(), new Vector3f(1f)));
        spawn(pointLightEntity);

        Entity spotLightEntity = new Entity();
        spotLightEntity.addComponent(new SpotLight(Color.RED, 1f));
        spotLightEntity.addComponent(new Transform(new Vector3f(8, 6, 0), new Vector3f(-45, 0, 0), new Vector3f(1f)));
        spawn(spotLightEntity);
    }
}
