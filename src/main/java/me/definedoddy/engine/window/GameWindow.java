package me.definedoddy.engine.window;

import me.definedoddy.toolkit.errors.ErrorPopupException;
import me.definedoddy.toolkit.glfw.GlfwMappings;
import me.definedoddy.toolkit.memory.Disposable;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.*;

public class GameWindow implements Disposable {
    private String title;
    private int width, height;

    private long windowId;

    // Options
    private boolean vSync = true;
    private boolean resizable = true;
    private boolean maximized = false;
    private boolean fullScreen = false;
    private boolean decorated = true;
    private boolean alwaysOnTop = false;

    public GameWindow(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
    }

    public void create() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) {
            throw new ErrorPopupException("GLFW Error", "Failed to initialise GLFW");
        }

        glfwDefaultWindowHints();

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GlfwMappings.toGlfwBoolean(resizable));
        glfwWindowHint(GLFW_MAXIMIZED, GlfwMappings.toGlfwBoolean(maximized));
        glfwWindowHint(GLFW_DECORATED, GlfwMappings.toGlfwBoolean(decorated));
        glfwWindowHint(GLFW_FLOATING, GlfwMappings.toGlfwBoolean(alwaysOnTop));

        windowId = glfwCreateWindow(width, height, title, fullScreen ? glfwGetPrimaryMonitor() : 0, 0);

        if (GlfwMappings.isNull(windowId)) {
            throw new ErrorPopupException("GLFW Error", "Failed to create window");
        }

        glfwMakeContextCurrent(windowId);
        createCapabilities();

        glfwWindowHint(GLFW_SRGB_CAPABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);

        glfwSwapInterval(vSync ? 1 : 0);

        glfwSetWindowCloseCallback(windowId, win -> glfwSetWindowShouldClose(win, true));

        glfwSetWindowSizeCallback(windowId, (win, w, h) -> {
            this.width = w;
            this.height = h;
        });

        if (!maximized && !fullScreen) {
            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            if (vidMode != null) glfwSetWindowPos(windowId, (vidMode.width() - width) / 2, (vidMode.height() - height) / 2);
        }
    }

    public void setVisible(boolean visible) {
        if (visible) glfwShowWindow(windowId);
        else glfwHideWindow(windowId);
    }

    public void setVSync(boolean vSync) {
        this.vSync = vSync;
        if (isCreated()) glfwSwapInterval(vSync ? 1 : 0);
    }

    public void setResizable(boolean resizable) {
        this.resizable = resizable;
        if (isCreated()) glfwWindowHint(GLFW_RESIZABLE, GlfwMappings.toGlfwBoolean(resizable));
    }

    public void setMaximized(boolean maximized) {
        this.maximized = maximized;
        if (isCreated()) glfwWindowHint(GLFW_MAXIMIZED, GlfwMappings.toGlfwBoolean(maximized));
    }

    public void setFullScreen(boolean fullScreen) {
        this.fullScreen = fullScreen;
        if (isCreated()) glfwSetWindowMonitor(windowId, fullScreen ? glfwGetPrimaryMonitor() : 0, 0, 0, width, height, GLFW_DONT_CARE);
    }

    public void setDecorated(boolean decorated) {
        this.decorated = decorated;
        if (isCreated()) glfwWindowHint(GLFW_DECORATED, GlfwMappings.toGlfwBoolean(decorated));
    }

    public void setAlwaysOnTop(boolean alwaysOnTop) {
        this.alwaysOnTop = alwaysOnTop;
        if (isCreated()) glfwWindowHint(GLFW_FLOATING, GlfwMappings.toGlfwBoolean(alwaysOnTop));
    }

    public void setTitle(String title) {
        this.title = title;
        if (isCreated()) glfwSetWindowTitle(windowId, title);
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        if (isCreated()) glfwSetWindowSize(windowId, width, height);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public long getWindowId() {
        return windowId;
    }

    public boolean isCreated() {
        return windowId != 0;
    }

    @Override
    public void dispose() {
        if (isCreated()) glfwDestroyWindow(windowId);
    }

    public boolean canUpdate() {
        if (!isCreated()) return false;
        return !glfwWindowShouldClose(windowId);
    }

    public void update() {
        if (!isCreated()) return;

        glfwPollEvents();

        glViewport(0, 0, width, height);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0.0f, width, 0.0f, height, 1.0f, -1.0f);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
    }
}
