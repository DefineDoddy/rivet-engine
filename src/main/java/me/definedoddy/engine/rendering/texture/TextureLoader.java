package me.definedoddy.engine.rendering.texture;

import me.definedoddy.toolkit.buffer.ImageUtils;
import me.definedoddy.toolkit.collections.HashMapList;
import me.definedoddy.toolkit.file.Resource;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

public class TextureLoader {
    private static final HashMapList<String, Texture> textures = new HashMapList<>();

    public static Texture loadTexture(Resource resource) {
        BufferedImage image = ImageUtils.readImage(resource.getStream());
        int width = image.getWidth();
        int height = image.getHeight();

        int textureId = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, textureId);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        ByteBuffer buf = ImageUtils.createByteBuffer(image);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buf);

        Texture tex = new Texture(textureId, width, height);
        textures.add(resource.getPath(), tex);

        return tex;
    }
}
