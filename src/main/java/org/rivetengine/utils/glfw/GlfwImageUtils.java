package org.rivetengine.utils.glfw;

import org.rivetengine.toolkit.buffer.ImageUtils;
import org.lwjgl.glfw.GLFWImage;

import java.awt.image.BufferedImage;

public class GlfwImageUtils {
    public static GLFWImage createGlfwImage(BufferedImage image) {
        GLFWImage glfwImage = GLFWImage.create();
        glfwImage.width(image.getWidth());
        glfwImage.height(image.getHeight());
        glfwImage.pixels(ImageUtils.createByteBuffer(image));
        return glfwImage;
    }

    public static GLFWImage.Buffer createGlfwImageBuffer(BufferedImage image) {
        GLFWImage.Buffer buffer = GLFWImage.create(1);
        buffer.put(0, createGlfwImage(image));
        return buffer;
    }
}
