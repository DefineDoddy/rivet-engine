package me.definedoddy.game.prefabs;

import org.joml.Vector3f;
import org.rivetengine.core.Assets;
import org.rivetengine.entity.Entity;
import org.rivetengine.entity.Prefab;
import org.rivetengine.entity.components.Transform;
import org.rivetengine.entity.components.rendering.Material;

public class GunPrefab implements Prefab {
    @Override
    public Entity create() {
        Entity entity = Assets.loadModel("assets/models/pistol/pistol.obj");

        Transform transform = entity.getComponent(Transform.class);
        transform.position.set(new Vector3f(0.3f, -0.4f, -0.6f));
        transform.rotation.set(new Vector3f(0f, 90f, 0f));
        transform.scale.set(0.001f);

        Material material = entity.getChild("Pistol_1").getComponent(Material.class);
        material.shininess = 1f;

        return entity;
    }
}
