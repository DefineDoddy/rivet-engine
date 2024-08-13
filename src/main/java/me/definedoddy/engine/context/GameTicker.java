package me.definedoddy.engine.context;

import me.definedoddy.engine.manager.GameManager;
import me.definedoddy.engine.physics.Time;
import me.definedoddy.engine.window.GameWindow;
import me.definedoddy.engine.utils.errors.ErrorWindowPopup;
import me.definedoddy.toolkit.debug.Debug;

public class GameTicker {
    private final GameWindow window;

    public GameTicker(GameWindow window) {
        this.window = window;
    }

    public void start() {
        this.init();

        while (window.canUpdate()) {
            try {
                this.update();
                long sleepTime = (long) (1000 / Time.getTargetFps());
                Thread.sleep(sleepTime);
            } catch (Exception e) {
                throw new ErrorWindowPopup("Game Tick Error", e.getMessage(), e);
            }
        }

        this.close();
    }

    public void init() {
        GameManager.init();
        GameContext.get().init();
        Time.init();

        Debug.log("Process started");
    }

    public void update() {
        GameContext.get().update();
        window.preUpdate();
        GameManager.update();
        Time.update();
        window.postUpdate();
    }

    public void close() {
        GameManager.stop();
        Debug.log("Process stopped");
    }
}
