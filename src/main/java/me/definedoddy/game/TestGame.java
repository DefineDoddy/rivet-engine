package com.rivetengine.game;

import me.definedoddy.engine.core.Game;
import me.definedoddy.engine.core.Time;
import me.definedoddy.engine.debug.Debug;
import me.definedoddy.engine.entity.Entity;
import me.definedoddy.engine.entity.components.Name;
import me.definedoddy.engine.entity.components.Transform;
import me.definedoddy.engine.entity.components.rendering.Camera;
import me.definedoddy.engine.entity.components.rendering.Skybox;
import me.definedoddy.engine.file.Assets;
import me.definedoddy.engine.icon.Icon;
import me.definedoddy.engine.input.KeyCode;
import me.definedoddy.engine.input.Keyboard;
import me.definedoddy.engine.rendering.cubemap.CubeMap;
import me.definedoddy.engine.window.GameWindow;
import me.definedoddy.toolkit.memory.Handle;

public class TestGame extends Game {
    public static void main(String[] args) {
        TestGame game = new TestGame();

        GameWindow window = new GameWindow("Test Game", 1280, 720);
        window.setIcon(Assets.load("assets/icon.png", Icon.class));
        game.setWindow(window);

        game.getProcess().start();
    }

    @Override
    public void init() {
        Entity mainCamera = new Entity();
        mainCamera.addComponent(new Transform());
        mainCamera.addComponent(new Camera());
        mainCamera.addComponent(new Name("Main Camera"));

        Handle<CubeMap> sky = Assets.load("assets/skybox", CubeMap.class);
        mainCamera.addComponent(new Skybox(sky));

        getActiveScene().addEntity(mainCamera);
    }

    @Override
    public void update(float dt) {
        if (Keyboard.get().wasKeyPressed(KeyCode.ESCAPE)) {
            getWindow().close();
        }

        if (Keyboard.get().wasKeyPressed(KeyCode.F11)) {
            getWindow().setMaximised(!getWindow().isMaximised());
        }

        if (Keyboard.get().wasKeyPressed(KeyCode.F1)) {
            Debug.log("FPS: " + Time.getFps());
            Debug.log("Entities: " + getActiveScene().getRootEntities().size());
        }
    }
}