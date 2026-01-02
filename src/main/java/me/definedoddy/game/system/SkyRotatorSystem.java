package me.definedoddy.game.system;

import org.rivetengine.core.Game;
import org.rivetengine.core.Scene;
import org.rivetengine.entity.Entity;
import org.rivetengine.entity.components.rendering.Skybox;
import org.rivetengine.system.GameSystem;

public class SkyRotatorSystem extends GameSystem {
    @Override
    public void update(Game game, float dt) {
        Scene scene = game.getActiveScene();
        Entity camera = scene.getEntity("Main Camera");

        if (camera == null) {
            return;
        }

        Skybox skybox = camera.getComponent(Skybox.class);

        if (skybox == null) {
            return;
        }

        skybox.rotation += 0.01f * dt;
    }
}