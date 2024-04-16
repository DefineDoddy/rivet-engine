package me.definedoddy.engine.manager;

import me.definedoddy.engine.input.Keyboard;
import me.definedoddy.engine.rendering.camera.Camera;

public class GameManager {
    private static final RenderEngine renderEngine = new RenderEngine();

    public static void init() {
        Keyboard.initAll();
        renderEngine.init();
    }

    public static void update() {
        Keyboard.updateAll();
        Camera.get().update();
        renderEngine.update();
    }

    public static RenderEngine getRenderEngine() {
        return renderEngine;
    }
}
