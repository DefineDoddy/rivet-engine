package org.rivetengine.core;

import java.util.List;
import java.util.ArrayList;

import org.rivetengine.debug.Debug;
import org.rivetengine.system.GameSystem;
import org.rivetengine.window.GameWindow;

public abstract class Game {
    public final Process process = new Process(this);
    private final List<GameSystem> systems = new ArrayList<>();

    public GameWindow window;
    private Scene activeScene;

    public void init() {
    }

    public void update(float dt) {
    }

    void loadDefaultScene() {
        if (activeScene == null) {
            activeScene = new Scene();
            activeScene.load();

            for (GameSystem system : systems) {
                system.onSceneLoaded(activeScene);
            }

            Debug.logInfo("Loaded default scene");
        }
    }

    public Scene getActiveScene() {
        return activeScene;
    }

    public <T extends Scene> T loadScene(Class<T> scene) {
        if (activeScene != null) {
            activeScene.unload();

            for (GameSystem system : systems) {
                system.onSceneUnloaded(activeScene);
            }
        }

        try {
            activeScene = scene.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create scene", e);
        }

        activeScene.load();

        for (GameSystem system : systems) {
            system.onSceneLoaded(activeScene);
        }

        Debug.logInfo("Loaded scene: " + scene.getSimpleName());
        return scene.cast(activeScene);
    }

    public void addSystem(GameSystem system) {
        systems.add(system);
    }

    public void removeSystem(GameSystem system) {
        systems.remove(system);
    }

    public List<GameSystem> getSystems() {
        return systems;
    }
}
