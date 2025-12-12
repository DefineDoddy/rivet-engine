package me.definedoddy.game.prefabs;

import org.rivetengine.entity.Entity;
import org.rivetengine.entity.Prefab;
import org.rivetengine.entity.components.Name;
import org.rivetengine.entity.components.physics.body.Rigidbody;
import org.rivetengine.entity.components.physics.collision.BoxCollider;

public class PlayerPrefab implements Prefab {
    @Override
    public Entity create() {
        Entity player = new Entity();

        player.addChild(new CameraPrefab().create());

        player.addComponent(new Name("Player"));
        player.addComponent(new Rigidbody());
        player.addComponent(new BoxCollider());

        return player;
    }
}
