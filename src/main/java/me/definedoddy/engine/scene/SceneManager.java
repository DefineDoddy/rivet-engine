package me.definedoddy.engine.scene;

import me.definedoddy.toolkit.debug.DebugLog;

public class SceneManager {
    private static Scene currentScene;

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
        }

        currentScene = createScene(scene);
        currentScene.load();
        DebugLog.info("Loaded scene: " + scene.getSimpleName());

        return scene.cast(currentScene);
    }

    private static Scene createScene(Class<? extends Scene> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create scene", e);
        }
    }
}
