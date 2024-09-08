package me.definedoddy.engine.scene;

import me.definedoddy.engine.debug.Debug;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class SceneManager {
    private static Scene currentScene;

    private static final Map<Consumer<Scene>, Boolean> sceneLoadedListeners = new HashMap<>();
    private static final Map<Consumer<Scene>, Boolean> sceneUnloadedListeners = new HashMap<>();

    public static void update() {
        if (currentScene != null) {
            currentScene.update();
        }
    }

    public static Scene getCurrentScene() {
        return currentScene;
    }

    public static <T extends Scene> T loadScene(Class<T> scene) {
        if (currentScene != null) {
            currentScene.unload();
            currentScene.setLoaded(false);

            sceneUnloadedListeners.entrySet().removeIf(entry -> {
                entry.getKey().accept(currentScene);
                return entry.getValue();
            });
        }

        currentScene = createScene(scene);
        currentScene.load();
        currentScene.setLoaded(true);

        sceneLoadedListeners.entrySet().removeIf(entry -> {
            entry.getKey().accept(currentScene);
            return entry.getValue();
        });

        Debug.log("Loaded scene: " + scene.getSimpleName());

        return scene.cast(currentScene);
    }

    public static void onSceneLoaded(Consumer<Scene> consumer, boolean once) {
        sceneLoadedListeners.put(consumer, once);
    }

    public static void onSceneUnloaded(Consumer<Scene> consumer, boolean once) {
        sceneUnloadedListeners.put(consumer, once);
    }

    private static Scene createScene(Class<? extends Scene> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create scene", e);
        }
    }
}
