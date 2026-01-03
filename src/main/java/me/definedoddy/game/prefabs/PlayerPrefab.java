package me.definedoddy.game.prefabs;

import me.definedoddy.game.component.PlayerController;
import org.joml.Vector3f;
import org.rivetengine.entity.Entity;
import org.rivetengine.entity.Prefab;
import org.rivetengine.entity.components.Transform;
import org.rivetengine.entity.components.physics.body.Rigidbody;
import org.rivetengine.entity.components.physics.collision.BoxCollider;

public class PlayerPrefab implements Prefab {
    private final Vector3f spawnPosition = new Vector3f(0, 2f, 0);
    private final Vector3f spawnRotation = new Vector3f(0, 0, 0);

    public PlayerPrefab() {
    }

    public PlayerPrefab(Vector3f spawnPosition, Vector3f spawnRotation) {
        this.spawnPosition.set(spawnPosition);
        this.spawnRotation.set(spawnRotation);
    }

    @Override
    public Entity create() {
        Entity player = new Entity("Player");

        player.addChild(new CameraPrefab().create());

        player.addComponent(new Transform(spawnPosition, spawnRotation, new Vector3f(1f)));
        player.addComponent(new Rigidbody());
        player.addComponent(new BoxCollider(new Vector3f(0.5f, 1f, 0.5f)));
        player.addComponent(new PlayerController());

        return player;
    }
}
