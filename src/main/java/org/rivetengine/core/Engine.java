package org.rivetengine.core;

import org.rivetengine.rendering.RenderEngine;

public class Engine {
    private static final RenderEngine renderEngine = new RenderEngine();

    public static void init() {
        renderEngine.init();
    }

    public static void stop() {
        renderEngine.stop();
    }

    public static RenderEngine getRenderer() {
        return renderEngine;
    }
}
