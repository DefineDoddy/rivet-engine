package org.rivetengine.input;

import org.rivetengine.window.GameWindow;
import org.lwjgl.glfw.GLFW;

import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class Keyboard {
    private final boolean[] keysPressed = new boolean[350];
    private final boolean[] lastKeysPressed = new boolean[350];

    public void init() {
        GLFW.glfwSetKeyCallback(GameWindow.get().getId(), this::keyCallback);
    }

    public void update() {
        Arrays.fill(lastKeysPressed, false);
    }

    private void keyCallback(long window, int key, int scancode, int action, int mods) {
        if (action == GLFW_PRESS) {
            lastKeysPressed[key] = true;
            keysPressed[key] = true;
        } else if (action == GLFW_RELEASE) {
            keysPressed[key] = false;
        }
    }

    public boolean isKeyPressed(KeyCode key) {
        return keysPressed[key.getValue()];
    }

    public boolean wasKeyPressed(KeyCode key) {
        return lastKeysPressed[key.getValue()];
    }
}
