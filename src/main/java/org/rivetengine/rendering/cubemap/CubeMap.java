package org.rivetengine.rendering.cubemap;

import org.rivetengine.rendering.object.Vao;
import org.rivetengine.rendering.texture.Texture;
import org.rivetengine.rendering.texture.TextureType;
import org.rivetengine.toolkit.buffer.BufferUtils;
import org.rivetengine.toolkit.memory.Disposable;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;

public class CubeMap implements Disposable {
    private final int textureId;
    private final Texture[] textures;

    private float size = 500f;
    private Vao vao;

    public CubeMap(int textureId, Texture[] textures) {
        this.textureId = textureId;
        this.textures = textures;
        this.vao = createVao();
    }

    private Vao createVao() {
        if (vao != null) {
            vao.dispose();
        }

        Vao vao = new Vao();

        FloatBuffer vertices = BufferUtils.createFloatBuffer(CubeMapUtils.genCubeMapVertices(size));
        vao.storeData(0, vertices, 3);

        return vao;
    }

    public void render() {
        vao.bind();

        GL30.glDepthMask(false);
        Texture.bind(textureId, TextureType.CUBEMAP, 0);

        GL30.glDrawArrays(GL30.GL_TRIANGLES, 0, vao.getVertexCount());

        Texture.unbind(TextureType.CUBEMAP, 0);
        GL30.glDepthMask(true);

        vao.unbind();
    }

    public int getTextureId() {
        return textureId;
    }

    public Texture[] getTextures() {
        return textures;
    }

    public Vao getVao() {
        return vao;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
        this.vao = createVao();
    }

    @Override
    public void dispose() {
        for (Texture texture : textures) {
            texture.dispose();
        }
        vao.dispose();
    }
}
