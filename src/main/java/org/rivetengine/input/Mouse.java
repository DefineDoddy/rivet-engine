package org.rivetengine.input;

import org.rivetengine.rendering.sprite.Icon;
import org.rivetengine.window.GameWindow;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class Mouse {
    private double xPos, yPos, lastX, lastY;
    private double deltaX, deltaY;
    private double scrollX, scrollY;

    private final boolean[] buttonPressed = new boolean[3];
    private final boolean[] lastButtonPressed = new boolean[3];

    private boolean isDragging;

    public void init() {
        GLFW.glfwSetCursorPosCallback(GameWindow.get().getId(), this::processMousePosition);
        GLFW.glfwSetMouseButtonCallback(GameWindow.get().getId(), this::processMouseButton);
        GLFW.glfwSetScrollCallback(GameWindow.get().getId(), this::processMouseScroll);
    }

    public void preUpdate() {
        Arrays.fill(lastButtonPressed, false);
    }

    public void postUpdate() {
        xPos = lastX;
        yPos = lastY;
        deltaX = 0;
        deltaY = 0;
        scrollX = 0d;
        scrollY = 0d;
    }

    private void processMousePosition(long window, double xPos, double yPos) {
        deltaX = xPos - lastX;
        deltaY = yPos - lastY;

        lastX = xPos;
        lastY = yPos;

        this.xPos = xPos;
        this.yPos = yPos;
        isDragging = buttonPressed[0] || buttonPressed[1] || buttonPressed[2];
    }

    private void processMouseButton(long window, int button, int action, int mods) {
        if (action == GLFW_PRESS) {
            buttonPressed[button] = true;
            lastButtonPressed[button] = true;
        } else if (action == GLFW_RELEASE) {
            buttonPressed[button] = false;
            isDragging = false;
        }
    }

    private void processMouseScroll(long window, double xOffset, double yOffset) {
        scrollX = xOffset;
        scrollY = yOffset;
    }

    public Vector2f getPosition() {
        return new Vector2f((float) xPos, (float) yPos);
    }

    public Vector2f getDelta() {
        return new Vector2f((float) deltaX, (float) deltaY);
    }

    public float getX() {
        return (float) xPos;
    }

    public float getY() {
        return (float) yPos;
    }

    public float getDx() {
        return (float) deltaX;
    }

    public float getDy() {
        return (float) deltaY;
    }

    public float getScrollX() {
        return (float) scrollX;
    }

    public float getScrollY() {
        return (float) scrollY;
    }

    public boolean isButtonPressed(MouseBtn button) {
        return buttonPressed[button.getId()];
    }

    public boolean wasButtonPressed(MouseBtn button) {
        return lastButtonPressed[button.getId()];
    }

    public boolean isDragging() {
        return isDragging;
    }

    public void setCursorVisible(boolean visible) {
        setCursorVisible(visible, false);
    }

    public void setCursorVisible(boolean visible, boolean locked) {
        if (visible) {
            GLFW.glfwSetInputMode(GameWindow.get().getId(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
        } else {
            GLFW.glfwSetInputMode(GameWindow.get().getId(), GLFW.GLFW_CURSOR,
                    locked ? GLFW.GLFW_CURSOR_DISABLED : GLFW.GLFW_CURSOR_HIDDEN);
        }
    }

    public void setCursorPosition(float x, float y) {
        GLFW.glfwSetCursorPos(GameWindow.get().getId(), x, y);
    }

    public void setCursorIcon(Icon icon, int originX, int originY) {
        long cursor = GLFW.glfwCreateCursor(icon.createGlfwImage(), originX, originY);
        GLFW.glfwSetCursor(GameWindow.get().getId(), cursor);
    }

    public void centerCursor() {
        setCursorPosition(GameWindow.get().getWidth() / 2f, GameWindow.get().getHeight() / 2f);
    }
}
