package me.definedoddy.engine.scene.environment;

import me.definedoddy.engine.manager.GameManager;
import me.definedoddy.engine.rendering.skybox.Skybox;
import me.definedoddy.engine.scene.Scene;
import me.definedoddy.engine.scene.SceneManager;

public class Environment {
    private final Scene scene;
    private Skybox skybox;

    public Environment(Scene scene) {
        this.scene = scene;

        SceneManager.onSceneLoaded((loadedScene) -> {
            if (loadedScene == scene) {
                GameManager.getRenderEngine().getSkyboxRenderer().setSkybox(skybox);
            }
        }, false);
    }

    public void setSkybox(Skybox skybox) {
        this.skybox = skybox;

        if (scene.isLoaded()) {
            GameManager.getRenderEngine().getSkyboxRenderer().setSkybox(skybox);
        }
    }

    public Skybox getSkybox() {
        return skybox;
    }
}
