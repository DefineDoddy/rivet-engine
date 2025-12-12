package com.rivetengine.game.scene;

import com.rivetengine.engine.core.Scene;
import com.rivetengine.engine.entity.Entity;
import com.rivetengine.engine.entity.components.Tags;
import com.rivetengine.engine.entity.components.Transform;
import com.rivetengine.engine.entity.components.physics.body.KinematicBody;
import com.rivetengine.engine.entity.components.physics.collision.BoxCollider;
import com.rivetengine.engine.entity.components.rendering.Camera;
import com.rivetengine.engine.entity.components.rendering.Mesh3d;
import com.rivetengine.engine.entity.components.rendering.Skybox;
import com.rivetengine.engine.entity.components.rendering.lighting.DirectionalLight;
import com.rivetengine.engine.entity.components.rendering.lighting.PointLight;
import com.rivetengine.engine.entity.components.rendering.lighting.SpotLight;
import com.rivetengine.engine.file.Assets;
import com.rivetengine.engine.rendering.cubemap.CubeMap;
import com.rivetengine.engine.rendering.mesh.Mesh;
import com.rivetengine.engine.rendering.primitives.Primitives;
import me.definedoddy.toolkit.memory.Handle;

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

        addEntity(groundEntity);

        // Add camera
        Entity mainCamera = new Entity();
        mainCamera.addComponent(new Camera());

        Handle<CubeMap> sky = Assets.load("assets/skybox", CubeMap.class);
        mainCamera.addComponent(new Skybox(sky));

        addEntity(mainCamera);

        addLighting();
    }

    private void addLighting() {
        Entity sunEntity = new Entity();
        sunEntity.addComponent(new DirectionalLight(Color.WHITE, 0.5f));
        sunEntity.addComponent(new Transform(new Vector3f(), new Vector3f(-45, 0, 0), new Vector3f(1f)));
        addEntity(sunEntity);

        Entity pointLightEntity = new Entity();
        pointLightEntity.addComponent(new PointLight(Color.WHITE, 0.5f));
        pointLightEntity.addComponent(new Transform(new Vector3f(0, 5, 0), new Vector3f(), new Vector3f(1f)));
        addEntity(pointLightEntity);

        Entity spotLightEntity = new Entity();
        spotLightEntity.addComponent(new SpotLight(Color.RED, 1f));
        spotLightEntity.addComponent(new Transform(new Vector3f(8, 6, 0), new Vector3f(-45, 0, 0), new Vector3f(1f)));
        addEntity(spotLightEntity);
    }
}
