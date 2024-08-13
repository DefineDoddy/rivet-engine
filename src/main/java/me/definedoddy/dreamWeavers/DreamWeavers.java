package me.definedoddy.dreamWeavers;

import me.definedoddy.dreamWeavers.models.Models;
import me.definedoddy.dreamWeavers.rendering.camera.FreeCamera;
import me.definedoddy.dreamWeavers.scene.TestWorld;
import me.definedoddy.engine.context.GameContext;
import me.definedoddy.engine.input.KeyCode;
import me.definedoddy.engine.input.Keyboard;
import me.definedoddy.engine.rendering.camera.Camera;
import me.definedoddy.engine.rendering.icon.Icon;
import me.definedoddy.engine.scene.SceneManager;
import me.definedoddy.engine.window.GameWindow;
import me.definedoddy.toolkit.file.Resource;

import java.awt.*;

public class DreamWeavers extends GameContext {
    public static void main(String[] args) {
        DreamWeavers instance = new DreamWeavers();

        GameWindow window = new GameWindow("Dream Weavers", 1280, 720);
        window.setIcon(Icon.fromFile(new Resource("assets/icon.png")));
        window.setBackgroundColour(Color.CYAN);
        instance.attachWindow(window);

        instance.startProcess(); // Must be the last line in the main method
    }

    @Override
    public void init() {
        Models.registerAll();

        SceneManager.loadScene(TestWorld.class);

        Camera.set(new FreeCamera());
        SceneManager.getCurrentScene().addEntity(Camera.get());
    }

    @Override
    public void update() {
        if (Keyboard.get().wasKeyPressed(KeyCode.ESCAPE)) {
            getWindow().close();
        }

        if (Keyboard.get().wasKeyPressed(KeyCode.F11)) {
            getWindow().setMaximized(!getWindow().isMaximized());
        }
    }
}