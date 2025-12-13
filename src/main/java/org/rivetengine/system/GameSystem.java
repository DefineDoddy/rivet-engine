package org.rivetengine.system;

import org.rivetengine.core.Game;
import org.rivetengine.core.Scene;

public abstract class GameSystem {
    public abstract void update(Game game, float dt);

    public void onSceneLoad(Scene scene) {
    }

    public void onSceneUnload(Scene scene) {
    }
}
