package me.definedoddy.engine.rendering.sprite;

import me.definedoddy.engine.utils.glfw.GlfwImageUtils;
import me.definedoddy.toolkit.buffer.ImageUtils;
import me.definedoddy.toolkit.file.File;
import me.definedoddy.toolkit.memory.Disposable;
import org.lwjgl.glfw.GLFWImage;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Sprite implements Disposable {
    private static final Map<String, Sprite> spriteCache = new HashMap<>();

    private final BufferedImage buffer;
    private final int width;
    private final int height;

    private GLFWImage glfwImage;
    private GLFWImage.Buffer glfwImageBuffer;

    public static Sprite fromFile(File file) {
        if (spriteCache.containsKey(file.getPath())) {
            return spriteCache.get(file.getPath());
        }

        BufferedImage image = ImageUtils.readImage(file.getStream());
        Sprite icon = new Sprite(image, image.getWidth(), image.getHeight());

        spriteCache.put(file.getPath(), icon);
        return icon;
    }

    public Sprite(BufferedImage buffer, int width, int height) {
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
