package org.rivetengine.core;

import org.rivetengine.debug.Debug;
import org.rivetengine.input.Input;
import org.rivetengine.utils.errors.ErrorWindowPopup;

public class Process {
    private final Game game;
    private final Engine engine;

    public Process(Game game) {
        this.game = game;
        this.engine = new Engine(game);
    }

    public void start() {
        Time.init();
        Input.keyboard.init();
        Input.mouse.init();

        game.init();
        engine.init();

        Debug.log("Process started");
        Debug.log("Creating window...");

        game.window.create();
        game.window.setVisible(true);

        Debug.log("Window created");

        while (game.window.canUpdate()) {
            try {
                game.window.preUpdate();
                Input.mouse.preUpdate();

                float dt = (float) Time.getDeltaTime();
                game.getSystems().forEach((system) -> system.update(game, dt));

                Input.keyboard.update();
                Input.mouse.postUpdate();

                Time.update();
                game.window.postUpdate();
                game.update(dt);

                long sleepTime = (long) (1000 / Time.getTargetFps());
                Thread.sleep(sleepTime);
            } catch (Exception e) {
                throw new ErrorWindowPopup("Game tick error", e.getMessage(), e);
            }
        }

        engine.dispose();
        Debug.log("Process stopped");
    }
}
