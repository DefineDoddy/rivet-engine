package me.definedoddy.engine.context;

import me.definedoddy.engine.window.GameWindow;

public abstract class GameContext {
    private static GameContext instance;

    private GameWindow window;
    private GameTicker ticker;

    public GameContext() {
        instance = this;
    }

    protected void attachWindow(GameWindow window) {
        this.window = window;
        this.ticker = new GameTicker(window);
    }

    protected void startProcess() {
        window.create();
        window.setVisible(true);
        ticker.start();
    }

    public static GameContext get() {
        return instance;
    }

    public GameWindow getWindow() {
        return window;
    }
}
