package me.definedoddy.engine.rendering.texture;

import me.definedoddy.toolkit.buffer.ImageUtils;
import me.definedoddy.toolkit.file.File;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;

public class TextureLoader {
    private static final HashMap<String, Texture> textureCache = new HashMap<>();

    public static Texture loadTexture2D(File file, TextureType type) {
        if (textureCache.containsKey(file.getPath())) {
            return textureCache.get(file.getPath());
        }

        BufferedImage image = ImageUtils.readImage(file.getStream());
        int width = image.getWidth();
        int height = image.getHeight();

        int textureId = glGenTextures();

        glBindTexture(type.getGlType(), textureId);
        glTexParameteri(type.getGlType(), GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(type.getGlType(), GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(type.getGlType(), GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(type.getGlType(), GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        ByteBuffer buf = ImageUtils.createByteBuffer(image);
        glTexImage2D(type.getGlType(), 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buf);

        Texture tex = new Texture(textureId, type, buf, width, height);
        textureCache.put(file.getPath(), tex);

        return tex;
    }

    public static Texture loadTextureCubeMap(File file) {
        if (textureCache.containsKey(file.getPath())) {
            return textureCache.get(file.getPath());
        }

        BufferedImage image = ImageUtils.readImage(file.getStream());
        int width = image.getWidth();
        int height = image.getHeight();

        int textureId = glGenTextures();
        ByteBuffer buf = ImageUtils.createByteBuffer(image);

        Texture tex = new Texture(textureId, TextureType.CUBEMAP, buf, width, height);
        textureCache.put(file.getPath(), tex);

        return tex;
    }
}
