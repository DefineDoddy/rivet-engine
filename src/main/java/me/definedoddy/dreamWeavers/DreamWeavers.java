package me.definedoddy.dreamWeavers;

import me.definedoddy.engine.context.GameContext;
import me.definedoddy.engine.rendering.icon.Icon;
import me.definedoddy.engine.window.GameWindow;
import me.definedoddy.toolkit.file.Resource;

import java.awt.*;

public class DreamWeavers extends GameContext {
    public static void main(String[] args) {
        DreamWeavers instance = new DreamWeavers();

        GameWindow window = new GameWindow("Dream Weavers", 1280, 720);
        window.setIcon(Icon.fromResource(new Resource("icon.png")));
        window.setBackgroundColour(Color.CYAN);
        instance.attachWindow(window);

        instance.startProcess();
    }
}