package org.rivetengine.core;

import org.rivetengine.physics.PhysicsSystem;
import org.rivetengine.rendering.RenderSystem;
import org.rivetengine.toolkit.memory.Disposable;

public class Engine implements Disposable {
    private final Game game;

    private final RenderSystem renderSystem = new RenderSystem();
    private final PhysicsSystem physicsSystem = new PhysicsSystem();

    public Engine(Game game) {
        this.game = game;
    }

    public void init() {
        game.addSystem(renderSystem);
        game.addSystem(physicsSystem);
    }

    @Override
    public void dispose() {
        game.removeSystem(renderSystem);
        renderSystem.dispose();
        game.removeSystem(physicsSystem);
    }
}
