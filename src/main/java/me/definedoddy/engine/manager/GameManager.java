package me.definedoddy.engine.manager;

import me.definedoddy.engine.rendering.camera.Camera;

public class GameManager {
    private static final RenderEngine renderEngine = new RenderEngine();

    public static void init() {
        renderEngine.init();
    }

    public static void update() {
        renderEngine.update();
        Camera.get().update();
    }

    public static RenderEngine getRenderEngine() {
        return renderEngine;
    }
}
