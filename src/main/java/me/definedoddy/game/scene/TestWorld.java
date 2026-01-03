package me.definedoddy.game.scene;

import org.rivetengine.core.Assets;
import org.rivetengine.core.Scene;
import org.rivetengine.entity.Entity;
import org.rivetengine.entity.components.Tags;
import org.rivetengine.entity.components.Transform;
import org.rivetengine.entity.components.physics.body.KinematicBody;
import org.rivetengine.entity.components.physics.collision.BoxCollider;
import org.rivetengine.entity.components.rendering.Material;
import org.rivetengine.entity.components.rendering.Mesh3d;
import org.rivetengine.entity.components.rendering.lighting.DirectionalLight;
import org.rivetengine.entity.components.rendering.lighting.PointLight;
import org.rivetengine.entity.components.rendering.lighting.SpotLight;
import org.rivetengine.rendering.Primitives;
import org.rivetengine.rendering.mesh.Mesh;
import org.rivetengine.rendering.texture.Texture;
import org.rivetengine.toolkit.memory.Handle;

import me.definedoddy.game.prefabs.PlayerPrefab;

import org.joml.Vector2f;
import org.joml.Vector3f;

import java.awt.*;

public class TestWorld extends Scene {
    @Override
    public void load() {
        Entity groundEntity = new Entity();

        Handle<Mesh> groundMesh = Primitives.quad(new Vector2f(100, 100));
        groundEntity.addComponent(new Mesh3d(groundMesh));

        groundEntity.addComponent(Transform.identity());
        groundEntity.addComponent(new Tags("ground"));
        groundEntity.addComponent(new BoxCollider(new Vector3f(50f, 1f, 50f)));
        groundEntity.addComponent(new KinematicBody());

        Handle<Texture> diffuseTex = Assets.load("assets/textures/asphalt/asphalt_diff.jpg", Texture.class);
        Material material = new Material(diffuseTex);
        material.shininess = 0.2f;
        groundEntity.addComponent(material);

        spawn(groundEntity);
        spawn(new PlayerPrefab(new Vector3f(0, 2f, -10f), new Vector3f(0, -90f, 0)).create());

        Entity stallEntity = Assets.loadModel("assets/obj/stall/stall.obj");
        stallEntity.addComponent(new BoxCollider(stallEntity));
        spawn(stallEntity);

        addLighting();
    }

    private void addLighting() {
        Entity sunEntity = new Entity();
        sunEntity.addComponent(new DirectionalLight(Color.WHITE, 0.8f));
        sunEntity.addComponent(Transform.fromXYZ(0, 10, 0).lookingAt(0, 0, 10));
        spawn(sunEntity);

        Entity pointLightEntity = new Entity();
        pointLightEntity.addComponent(new PointLight(Color.WHITE, 5f));
        pointLightEntity.addComponent(Transform.fromXYZ(0, 3f, 0).lookingAt(0, 0, 0));
        spawn(pointLightEntity);

        Entity spotLightEntity = new Entity();
        spotLightEntity.addComponent(new SpotLight(Color.RED, 10f));
        spotLightEntity.addComponent(Transform.fromXYZ(8f, 6f, 0f).lookingAt(0, 0, 0));
        spawn(spotLightEntity);
    }
}
