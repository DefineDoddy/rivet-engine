package org.rivetengine.core;

import org.rivetengine.rendering.RenderSystem;
import org.rivetengine.toolkit.memory.Disposable;

public class Engine implements Disposable {
    private final Game game;

    private final RenderSystem renderer = new RenderSystem();

    public Engine(Game game) {
        this.game = game;
    }

    public void update(float dt) {
        renderer.update(game, dt);
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }
}
