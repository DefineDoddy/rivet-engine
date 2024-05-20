package me.definedoddy.engine.rendering.texture;

import me.definedoddy.toolkit.buffer.ImageUtils;
import me.definedoddy.toolkit.file.Resource;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;

public class TextureLoader {
    private static final HashMap<String, Texture> textures = new HashMap<>();

    public static Texture loadTexture2D(Resource resource, TextureType type) {
        if (textures.containsKey(resource.getPath())) {
            return textures.get(resource.getPath());
        }

        BufferedImage image = ImageUtils.readImage(resource.getStream());
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
        textures.put(resource.getPath(), tex);

        return tex;
    }

    public static Texture loadTextureCubeMap(Resource resource) {
        if (textures.containsKey(resource.getPath())) {
            return textures.get(resource.getPath());
        }

        TextureType type = TextureType.CUBEMAP;

        BufferedImage image = ImageUtils.readImage(resource.getStream());
        int width = image.getWidth();
        int height = image.getHeight();

        int textureId = glGenTextures();

        glBindTexture(type.getGlType(), textureId);
        glTexParameteri(type.getGlType(), GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(type.getGlType(), GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(type.getGlType(), GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(type.getGlType(), GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        ByteBuffer buf = ImageUtils.createByteBuffer(image);

        Texture tex = new Texture(textureId, type, buf, width, height);
        textures.put(resource.getPath(), tex);

        return tex;
    }
}
