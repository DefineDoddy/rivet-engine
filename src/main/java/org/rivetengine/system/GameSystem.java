package org.rivetengine.system;

import org.rivetengine.core.Game;
import org.rivetengine.core.Scene;

public abstract class GameSystem {
    public abstract void update(Game game, float dt);

    public void onSceneLoaded(Scene scene) {
    }

    public void onSceneUnloaded(Scene scene) {
    }
}
