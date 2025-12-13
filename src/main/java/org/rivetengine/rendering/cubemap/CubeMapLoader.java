package org.rivetengine.rendering.cubemap;

import org.rivetengine.rendering.texture.Texture;
import org.rivetengine.rendering.texture.TextureLoader;
import org.rivetengine.rendering.texture.TextureType;
import org.rivetengine.toolkit.file.Resource;

import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_WRAP_R;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X;

public class CubeMapLoader {
    public static CubeMap load(String folderPath) {
        return load(
                loadFace(folderPath + "/right.png"),
                loadFace(folderPath + "/left.png"),
                loadFace(folderPath + "/top.png"),
                loadFace(folderPath + "/bottom.png"),
                loadFace(folderPath + "/back.png"),
                loadFace(folderPath + "/front.png"));
    }

    public static CubeMap load(String basePath, String extension) {
        return load(
                loadFace(basePath + "_right." + extension),
                loadFace(basePath + "_left." + extension),
                loadFace(basePath + "_top." + extension),
                loadFace(basePath + "_bottom." + extension),
                loadFace(basePath + "_back." + extension),
                loadFace(basePath + "_front." + extension));
    }

    private static Texture loadFace(String path) {
        return TextureLoader.loadTextureCubeMap(new Resource(path));
    }

    /**
     * Load a cube map from a set of 6 textures.
     * <br>
     * Texture order: right, left, top, bottom, back, front.
     *
     * @param textures the textures
     * @return the cube map
     */
    public static CubeMap load(Texture... textures) {
        if (textures.length != 6) {
            throw new IllegalArgumentException("CubeMap must have 6 textures");
        }

        int textureId = GL11.glGenTextures();
        Texture.bind(textureId, TextureType.CUBEMAP, 0);

        for (int i = 0; i < textures.length; i++) {
            Texture texture = textures[i];

            glTexImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL_RGBA,
                    texture.width, texture.height,
                    0, GL_RGBA, GL_UNSIGNED_BYTE, texture.data);
        }

        int glType = TextureType.CUBEMAP.getGlType();
        glTexParameteri(glType, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(glType, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(glType, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);
        glTexParameteri(glType, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(glType, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

        Texture.unbind(TextureType.CUBEMAP, 0);

        return new CubeMap(textureId, textures);
    }
}
