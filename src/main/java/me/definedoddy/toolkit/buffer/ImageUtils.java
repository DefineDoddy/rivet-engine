package me.definedoddy.toolkit.buffer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ImageUtils {
    public static ByteBuffer createByteBuffer(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        int[] pixels = image.getRGB(0, 0, width, height, null, 0, width);
        ByteBuffer rgbaBuffer = ByteBuffer.allocateDirect(width * height * 4);
        rgbaBuffer.order(ByteOrder.nativeOrder());

        for (int pixel : pixels) {
            rgbaBuffer.put((byte) ((pixel >> 16) & 0xFF)); // Red
            rgbaBuffer.put((byte) ((pixel >> 8) & 0xFF));  // Green
            rgbaBuffer.put((byte) (pixel & 0xFF));         // Blue
            rgbaBuffer.put((byte) ((pixel >> 24) & 0xFF)); // Alpha
        }

        return rgbaBuffer.flip();
    }

    public static BufferedImage readImage(InputStream stream) {
        try {
            return ImageIO.read(stream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to open image", e);
        }
    }
}
