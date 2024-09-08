package me.definedoddy.engine.input;

import me.definedoddy.engine.icon.Icon;
import me.definedoddy.engine.window.GameWindow;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import java.util.Arrays;
import java.util.List;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class Mouse {
    private static List<Mouse> connected;

    private double xPos, yPos, lastX, lastY;
    private double scrollX, scrollY;

    private final boolean[] buttonPressed = new boolean[3];
    private final boolean[] lastButtonPressed = new boolean[3];

    private boolean isDragging;

    public static Mouse get() {
        return connected.getFirst();
    }

    public static void initAll() {
        connected = Input.getConnectedMice();
        connected.forEach(Mouse::init);
    }

    public static void preUpdateAll() {
        connected.forEach(Mouse::preUpdate);
    }

    public static void postUpdateAll() {
        connected.forEach(Mouse::postUpdate);
    }

    private void init() {
        GLFW.glfwSetCursorPosCallback(GameWindow.get().getWindowId(), this::processMousePosition);
        GLFW.glfwSetMouseButtonCallback(GameWindow.get().getWindowId(), this::processMouseButton);
        GLFW.glfwSetScrollCallback(GameWindow.get().getWindowId(), this::processMouseScroll);
    }

    private void preUpdate() {
        Arrays.fill(lastButtonPressed, false);
    }

    private void postUpdate() {
        xPos = lastX;
        yPos = lastY;
        scrollX = 0d;
        scrollY = 0d;
    }

    private void processMousePosition(long window, double xPos, double yPos) {
        lastX = xPos;
        lastY = yPos;
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
        return new Vector2f((float) (lastX - xPos), (float) (lastY - yPos));
    }

    public float getX() {
        return (float) xPos;
    }

    public float getY() {
        return (float) yPos;
    }

    public float getDX() {
        return (float) (lastX - xPos);
    }

    public float getDY() {
        return (float) (lastY - yPos);
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
        GLFW.glfwSetInputMode(GameWindow.get().getWindowId(), GLFW.GLFW_CURSOR, visible ? GLFW.GLFW_CURSOR_NORMAL : GLFW.GLFW_CURSOR_DISABLED);
    }

    public void setCursorPosition(float x, float y) {
        GLFW.glfwSetCursorPos(GameWindow.get().getWindowId(), x, y);
    }

    public void setCursorIcon(Icon icon, int originX, int originY) {
        long cursor = GLFW.glfwCreateCursor(icon.createGlfwImage(), originX, originY);
        GLFW.glfwSetCursor(GameWindow.get().getWindowId(), cursor);
    }
}
