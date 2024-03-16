package me.definedoddy.engine.context;

import me.definedoddy.engine.physics.Time;
import me.definedoddy.engine.window.GameWindow;
import me.definedoddy.engine.utils.errors.ErrorWindowPopup;

public class GameTicker {
    private final GameWindow window;

    public GameTicker(GameWindow window) {
        this.window = window;
    }

    public void start() {
        while (window.canUpdate()) {
            try {
                this.update();
                long sleepTime = (long) (1000 / Time.getTargetFps());
                Thread.sleep(sleepTime);
            } catch (Exception e) {
                throw new ErrorWindowPopup("Game Tick Error", e.getMessage(), e);
            }
        }
    }

    public void update() {
        window.update();
        Time.update();
    }
}
