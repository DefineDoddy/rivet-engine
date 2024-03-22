package me.definedoddy.engine.rendering.icon;

import me.definedoddy.engine.utils.glfw.GlfwImageUtils;
import me.definedoddy.toolkit.buffer.ImageUtils;
import me.definedoddy.toolkit.file.Resource;
import me.definedoddy.toolkit.memory.Disposable;
import org.lwjgl.glfw.GLFWImage;

import java.awt.image.BufferedImage;

public class Icon implements Disposable {
    private final BufferedImage buffer;
    private final int width;
    private final int height;

    private GLFWImage glfwImage;
    private GLFWImage.Buffer glfwImageBuffer;

    public static Icon fromResource(Resource resource) {
        BufferedImage image = ImageUtils.readImage(resource.getStream());
        return new Icon(image, image.getWidth(), image.getHeight());
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
