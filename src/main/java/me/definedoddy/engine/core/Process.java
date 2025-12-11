package me.definedoddy.engine.core;

import me.definedoddy.engine.debug.Debug;
import me.definedoddy.engine.input.Keyboard;
import me.definedoddy.engine.input.Mouse;
import me.definedoddy.engine.physics.PhysicsContainer;
import me.definedoddy.engine.ui.UI;
import me.definedoddy.engine.utils.errors.ErrorWindowPopup;

public class Process {
    private final Game game;

    public Process(Game game) {
        this.game = game;
    }

    public void start() {
        Time.init();
        Engine.init();
        Keyboard.initAll();
        Mouse.initAll();
        UI.init();

        game.init();

        Debug.log("Process started");
        Debug.log("Creating window...");

        game.getWindow().create();
        game.getWindow().setVisible(true);

        Debug.log("Window created");

        while (game.getWindow().canUpdate()) {
            try {
                game.getWindow().preUpdate();

                // Input
                Mouse.preUpdateAll();

                // Logic
                PhysicsContainer.update();

                // Rendering
                Engine.getRenderer().update();
                UI.update();

                // Input
                Keyboard.updateAll();
                Mouse.postUpdateAll();

                Time.update();
                game.getWindow().postUpdate();

                game.update((float) Time.getDeltaTime());
                long sleepTime = (long) (1000 / Time.getTargetFps());
                Thread.sleep(sleepTime);
            } catch (Exception e) {
                throw new ErrorWindowPopup("Game tick error", e.getMessage(), e);
            }
        }

        Engine.stop();
        Debug.stop();

        Debug.log("Process stopped");
    }
}
