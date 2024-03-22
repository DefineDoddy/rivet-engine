package me.definedoddy.engine.manager;

public class GameManager {
    private static final RenderEngine renderEngine = new RenderEngine();

    public static void init() {
        renderEngine.init();
    }

    public static void update() {
        renderEngine.update();
    }
}
