package me.definedoddy.engine.system;

import me.definedoddy.engine.core.Game;
import me.definedoddy.engine.core.Scene;

public abstract class GameSystem {
    public abstract void update(Game engine, float dt);

    public void onSceneLoad(Scene scene) {
    }

    public void onSceneUnload(Scene scene) {
    }
}
