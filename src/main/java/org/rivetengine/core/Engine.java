package org.rivetengine.core;

import org.rivetengine.physics.PhysicsSystem;
import org.rivetengine.rendering.CameraSystem;
import org.rivetengine.rendering.RenderSystem;
import org.rivetengine.toolkit.memory.Disposable;

public class Engine implements Disposable {
    private final Game game;

    private RenderSystem renderSystem;
    private PhysicsSystem physicsSystem;
    private CameraSystem cameraSystem;

    public Engine(Game game) {
        this.game = game;
    }

    public void init() {
        renderSystem = new RenderSystem();
        physicsSystem = new PhysicsSystem();
        cameraSystem = new CameraSystem();

        game.addSystem(renderSystem);
        game.addSystem(physicsSystem);
        game.addSystem(cameraSystem);
    }

    @Override
    public void dispose() {
        if (renderSystem != null) {
            game.removeSystem(renderSystem);
            renderSystem.dispose();
        }

        if (physicsSystem != null) {
            game.removeSystem(physicsSystem);
        }

        if (cameraSystem != null) {
            game.removeSystem(cameraSystem);
        }
    }
}
