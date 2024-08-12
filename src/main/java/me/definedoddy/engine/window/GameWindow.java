package me.definedoddy.engine.window;

import me.definedoddy.engine.rendering.icon.Icon;
import me.definedoddy.engine.utils.errors.FatalErrorWindowPopup;
import me.definedoddy.engine.utils.glfw.GlfwMappings;
import me.definedoddy.toolkit.memory.Disposable;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;

import java.awt.*;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP;
import static org.lwjgl.opengl.GL32.GL_TEXTURE_CUBE_MAP_SEAMLESS;

public class GameWindow implements Disposable {
    private static GameWindow instance;

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
    private Icon icon;

    private Color backgroundColor = Color.BLACK;

    public GameWindow(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        instance = this;
    }

    public void create() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) {
            throw new FatalErrorWindowPopup("GLFW Error", "Failed to initialise GLFW");
        }

        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        if (vidMode == null) {
            throw new FatalErrorWindowPopup("GLFW Error", "Failed to get video mode");
        }

        glfwDefaultWindowHints();

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GlfwMappings.toGlfwBoolean(resizable));
        glfwWindowHint(GLFW_MAXIMIZED, GlfwMappings.toGlfwBoolean(maximized));
        glfwWindowHint(GLFW_DECORATED, GlfwMappings.toGlfwBoolean(decorated));
        glfwWindowHint(GLFW_FLOATING, GlfwMappings.toGlfwBoolean(alwaysOnTop));

        windowId = glfwCreateWindow(vidMode.width(), vidMode.height(), title, fullScreen ? glfwGetPrimaryMonitor() : 0, 0);

        if (GlfwMappings.isNull(windowId)) {
            throw new FatalErrorWindowPopup("GLFW Error", "Failed to create window");
        }

        glfwMakeContextCurrent(windowId);
        createCapabilities();

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_SRGB_CAPABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);

        glfwSwapInterval(vSync ? 1 : 0);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glEnable(GL_DEPTH_TEST);

        glEnable(GL_TEXTURE_2D);
        glEnable(GL_TEXTURE_CUBE_MAP);
        glEnable(GL_TEXTURE_CUBE_MAP_SEAMLESS);

        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);

        // FIXME: maximise not working properly on startup
        if (!fullScreen && !maximized) glfwSetWindowSize(windowId, width, height);

        glfwSetWindowCloseCallback(windowId, win -> glfwSetWindowShouldClose(win, true));

        glfwSetWindowSizeCallback(windowId, (win, w, h) -> {
            this.width = w;
            this.height = h;
        });

        if (!maximized && !fullScreen) {
            glfwSetWindowPos(windowId,(vidMode.width() - width) / 2, (vidMode.height() - height) / 2);
        }

        if (icon != null) glfwSetWindowIcon(windowId, icon.createGlfwImageBuffer());

        glClearColor(backgroundColor.getRed() / 255f,
                backgroundColor.getGreen() / 255f,
                backgroundColor.getBlue() / 255f,
                backgroundColor.getAlpha() / 255f
        );
    }

    public static GameWindow get() {
        return instance;
    }

    public void preUpdate() {
        if (!isCreated()) return;

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        glClearColor(backgroundColor.getRed() / 255f,
                backgroundColor.getGreen() / 255f,
                backgroundColor.getBlue() / 255f,
                backgroundColor.getAlpha() / 255f
        );
    }

    public void postUpdate() {
        if (!isCreated()) return;

        glfwSwapBuffers(windowId);

        glViewport(0, 0, width, height);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0f, width, 0f, height, 1f, -1f);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        glfwPollEvents();
    }

    public void setBackgroundColour(Color colour) {
        this.backgroundColor = colour;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
        if (isCreated()) {
            glfwSetWindowIcon(windowId, icon.createGlfwImageBuffer());
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
        if (isCreated()) {
            glfwSetWindowAttrib(windowId, GLFW_RESIZABLE, GlfwMappings.toGlfwBoolean(resizable));
        }
    }

    public void setMaximized(boolean maximized) {
        this.maximized = maximized;
        if (isCreated()) {
            if (maximized) glfwMaximizeWindow(windowId);
            else glfwRestoreWindow(windowId);
        }
    }

    public boolean isMaximized() {
        return maximized;
    }

    public void setFullScreen(boolean fullScreen) {
        this.fullScreen = fullScreen;
        if (isCreated()) {
            glfwSetWindowMonitor(windowId, fullScreen ? glfwGetPrimaryMonitor() : 0,
                    0, 0, width, height, GLFW_DONT_CARE);
        }
    }

    public void setDecorated(boolean decorated) {
        this.decorated = decorated;
        if (isCreated()) {
            glfwSetWindowAttrib(windowId, GLFW_DECORATED, GlfwMappings.toGlfwBoolean(decorated));
        }
    }

    public void setAlwaysOnTop(boolean alwaysOnTop) {
        this.alwaysOnTop = alwaysOnTop;
        if (isCreated()) {
            glfwSetWindowAttrib(windowId, GLFW_FLOATING, GlfwMappings.toGlfwBoolean(alwaysOnTop));
        }
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
        if (isCreated()) {
            glfwFreeCallbacks(windowId);

            glfwDestroyWindow(windowId);
            windowId = 0;

            glfwTerminate();
            glfwSetErrorCallback(null).free();
        }
    }

    public boolean canUpdate() {
        if (!isCreated()) return false;
        return !glfwWindowShouldClose(windowId);
    }

    public void close() {
        if (isCreated()) glfwSetWindowShouldClose(windowId, true);
    }
}
