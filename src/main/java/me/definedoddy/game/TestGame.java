package me.definedoddy.game;

import org.rivetengine.core.Assets;
import org.rivetengine.core.Game;
import org.rivetengine.core.Time;
import org.rivetengine.debug.Debug;
import org.rivetengine.input.Input;
import org.rivetengine.input.KeyCode;
import org.rivetengine.rendering.sprite.Icon;
import org.rivetengine.window.GameWindow;

import me.definedoddy.game.scene.TestWorld;
import me.definedoddy.game.system.PlayerInputSystem;

public class TestGame extends Game {
    public static void main(String[] args) {
        TestGame game = new TestGame();

        game.window = new GameWindow("Test Game", 1280, 720);
        game.window.setIcon(Assets.load("assets/icon.png", Icon.class));

        game.process.start();
    }

    @Override
    public void init() {
        Input.mouse.setCursorVisible(false, true);

        addSystem(new PlayerInputSystem());
        loadScene(TestWorld.class);
    }

    @Override
    public void update(float dt) {
        if (Input.keyboard.wasKeyPressed(KeyCode.ESCAPE)) {
            window.close();
        }

        if (Input.keyboard.wasKeyPressed(KeyCode.F11)) {
            window.setMaximised(!window.isMaximised());
        }

        if (Input.keyboard.wasKeyPressed(KeyCode.F1)) {
            Debug.logInfo("FPS: " + Time.getFps());
            Debug.logInfo("Entities: " + getActiveScene().getAllEntities().size());
        }
    }
}