package me.definedoddy.engine.manager;

import me.definedoddy.engine.input.Keyboard;
import me.definedoddy.engine.input.Mouse;
import me.definedoddy.engine.rendering.camera.Camera;
import me.definedoddy.engine.scene.SceneManager;
import me.definedoddy.toolkit.debug.Debug;

public class GameManager {
    private static final RenderEngine renderEngine = new RenderEngine();

    public static void init() {
        renderEngine.init();
        Keyboard.initAll();
        Mouse.initAll();
    }

    public static void update() {
        // Input
        Mouse.preUpdateAll();

        // Logic
        SceneManager.update();

        // Rendering
        Camera.updateAll();
        renderEngine.update();

        // Input
        Keyboard.updateAll();
        Mouse.postUpdateAll();
    }

    public static void stop() {
        renderEngine.stop();
        Debug.stop();
    }

    public static RenderEngine getRenderEngine() {
        return renderEngine;
    }
}
