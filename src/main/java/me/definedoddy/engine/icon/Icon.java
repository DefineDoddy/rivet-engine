package me.definedoddy.engine.icon;

import me.definedoddy.engine.utils.glfw.GlfwImageUtils;
import me.definedoddy.toolkit.buffer.ImageUtils;
import me.definedoddy.toolkit.file.File;
import me.definedoddy.toolkit.memory.Disposable;
import org.lwjgl.glfw.GLFWImage;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Icon implements Disposable {
    private static final Map<String, Icon> iconCache = new HashMap<>();

    private final BufferedImage buffer;
    private final int width;
    private final int height;

    private GLFWImage glfwImage;
    private GLFWImage.Buffer glfwImageBuffer;

    public static Icon fromFile(File file) {
        if (iconCache.containsKey(file.getPath())) {
            return iconCache.get(file.getPath());
        }

        BufferedImage image = ImageUtils.readImage(file.getStream());
        Icon icon = new Icon(image, image.getWidth(), image.getHeight());

        iconCache.put(file.getPath(), icon);
        return icon;
    }

    public Icon(BufferedImage buffer, int width, int height) {
        this.buffer = buffer;
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public BufferedImage getBuffer() {
        return buffer;
    }

    public GLFWImage createGlfwImage() {
        if (glfwImage == null) {
            glfwImage = GlfwImageUtils.createGlfwImage(buffer);
        }
        return glfwImage;
    }

    public GLFWImage.Buffer createGlfwImageBuffer() {
        if (glfwImageBuffer == null) {
            glfwImageBuffer = GlfwImageUtils.createGlfwImageBuffer(buffer);
        }
        return glfwImageBuffer;
    }

    @Override
    public void dispose() {
        buffer.flush();
        glfwImage = null;
        glfwImageBuffer = null;
    }
}
