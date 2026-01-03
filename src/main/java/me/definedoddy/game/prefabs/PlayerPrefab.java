package me.definedoddy.game.prefabs;

import me.definedoddy.game.component.PlayerController;
import org.joml.Vector3f;
import org.rivetengine.entity.Entity;
import org.rivetengine.entity.Prefab;
import org.rivetengine.entity.components.Transform;
import org.rivetengine.entity.components.physics.body.Rigidbody;
import org.rivetengine.entity.components.physics.collision.BoxCollider;

public class PlayerPrefab implements Prefab {
    @Override
    public Entity create() {
        Entity player = new Entity("Player");

        player.addChild(new CameraPrefab().create());

        player.addComponent(Transform.fromXYZ(0, 2f, 0));
        player.addComponent(new Rigidbody());
        player.addComponent(new BoxCollider(new Vector3f(0.5f, 1f, 0.5f)));
        player.addComponent(new PlayerController());

        return player;
    }
}
