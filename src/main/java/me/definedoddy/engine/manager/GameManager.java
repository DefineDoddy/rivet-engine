package me.definedoddy.engine.manager;

import me.definedoddy.engine.input.Keyboard;
import me.definedoddy.engine.input.Mouse;
import me.definedoddy.engine.rendering.camera.Camera;

public class GameManager {
    private static final RenderEngine renderEngine = new RenderEngine();

    public static void init() {
        Keyboard.initAll();
        Mouse.initAll();
        Camera.get().init();
        renderEngine.init();
    }

    public static void update() {
        Mouse.preUpdateAll();
        Camera.get().update();
        renderEngine.update();
        Keyboard.updateAll();
        Mouse.postUpdateAll();
    }

    public static RenderEngine getRenderEngine() {
        return renderEngine;
    }
}
