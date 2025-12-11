package me.definedoddy.game;

import me.definedoddy.engine.context.GameContext;
import me.definedoddy.engine.context.Time;
import me.definedoddy.engine.debug.Debug;
import me.definedoddy.engine.icon.Icon;
import me.definedoddy.engine.input.KeyCode;
import me.definedoddy.engine.input.Keyboard;
import me.definedoddy.engine.scene.SceneManager;
import me.definedoddy.engine.window.GameWindow;
import me.definedoddy.game.entity.player.Player;
import me.definedoddy.game.models.Models;
import me.definedoddy.game.scene.TestWorld;
import me.definedoddy.toolkit.file.Resource;
import org.joml.Vector3f;

public class GameLauncher extends GameContext {
    public static void main(String[] args) {
        GameLauncher instance = new GameLauncher();

        GameWindow window = new GameWindow("Untitled Game", 1280, 720);
        window.setIcon(Icon.fromFile(new Resource("assets/icon.png")));
        instance.attachWindow(window);

        instance.startProcess(); // Must be the last line in the main method
    }

    @Override
    public void init() {
        Models.registerAll();

        SceneManager.loadScene(TestWorld.class);
        SceneManager.getCurrentScene().addEntity(new Player(new Vector3f()));
    }

    @Override
    public void update() {
        if (Keyboard.get().wasKeyPressed(KeyCode.ESCAPE)) {
            getWindow().close();
        }

        if (Keyboard.get().wasKeyPressed(KeyCode.F11)) {
            getWindow().setMaximized(!getWindow().isMaximized());
        }

        if (Keyboard.get().wasKeyPressed(KeyCode.F1)) {
            Debug.log("FPS: " + Time.getFps());
            Debug.log("Entities: " + SceneManager.getCurrentScene().getEntities().size());
        }
    }
}