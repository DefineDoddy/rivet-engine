package me.definedoddy.engine.rendering.cubemap;

import me.definedoddy.engine.rendering.texture.Texture;
import me.definedoddy.engine.rendering.texture.TextureType;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_WRAP_R;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X;

public class CubeMapLoader {
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
                    texture.getWidth(), texture.getHeight(),
                    0, GL_RGBA, GL_UNSIGNED_BYTE, texture.getData()
            );
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
