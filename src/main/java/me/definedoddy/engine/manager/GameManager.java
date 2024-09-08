package me.definedoddy.engine.manager;

import me.definedoddy.engine.debug.Debug;
import me.definedoddy.engine.input.Keyboard;
import me.definedoddy.engine.input.Mouse;
import me.definedoddy.engine.scene.SceneManager;
import me.definedoddy.engine.ui.UI;

public class GameManager {
    private static final RenderEngine renderEngine = new RenderEngine();

    public static void init() {
        renderEngine.init();
        Keyboard.initAll();
        Mouse.initAll();
        UI.init();
    }

    public static void update() {
        // Input
        Mouse.preUpdateAll();

        // Logic
        SceneManager.update();

        // Rendering
        renderEngine.update();

        UI.update();

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
