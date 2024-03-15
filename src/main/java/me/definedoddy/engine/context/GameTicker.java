package me.definedoddy.engine.context;

import me.definedoddy.engine.physics.Time;
import me.definedoddy.engine.window.GameWindow;
import me.definedoddy.toolkit.debug.DebugLog;
import me.definedoddy.toolkit.errors.ErrorPopupException;

public class GameTicker {
    private final GameWindow window;

    public GameTicker(GameWindow window) {
        this.window = window;
    }

    public void start() {
        while (window.canUpdate()) {
            try {
                update();
                long sleepTime = (long) (1000 / Time.getTargetFps());
                Thread.sleep(sleepTime);
                DebugLog.info("FPS: " + Time.getFps() + " | Delta Time: " + Time.getDeltaTime() + " | Sleep Time: " + sleepTime + "ms");
            } catch (Exception e) {
                throw new ErrorPopupException("Game Tick Error", e.getMessage(), e);
            }
        }
    }

    public void update() {
        window.update();
        Time.update();
    }
}
