package me.definedoddy.engine.context;

import me.definedoddy.engine.window.GameWindow;

public abstract class GameContext {
    private GameWindow window;
    private GameTicker ticker;

    protected void attachWindow(GameWindow window) {
        this.window = window;
        this.ticker = new GameTicker(window);
    }

    protected void startProcess() {
        window.create();
        window.setVisible(true);
        ticker.start();
    }

    public GameWindow getWindow() {
        return window;
    }
}
