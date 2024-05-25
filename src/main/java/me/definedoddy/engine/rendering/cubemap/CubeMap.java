package me.definedoddy.engine.rendering.cubemap;

import me.definedoddy.engine.rendering.object.Vao;
import me.definedoddy.engine.rendering.object.model.ModelUtils;
import me.definedoddy.engine.rendering.texture.Texture;
import me.definedoddy.engine.rendering.texture.TextureType;
import me.definedoddy.toolkit.buffer.BufferUtils;
import me.definedoddy.toolkit.memory.Disposable;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;

public class CubeMap implements Disposable {
    private final int textureID;
    private final Texture[] textures;

    private float size = 500f;
    private Vao vao;

    public CubeMap(int textureID, Texture[] textures) {
        this.textureID = textureID;
        this.textures = textures;
        this.vao = createVao();
    }

    private Vao createVao() {
        if (vao != null) vao.dispose();
        Vao vao = new Vao();

        FloatBuffer vertices = BufferUtils.createFloatBuffer(ModelUtils.genCubeMapVertices(size));
        vao.storeData(0, vertices, 3);

        return vao;
    }

    public void render() {
        vao.bind();

        GL30.glDepthMask(false);
        Texture.bind(textureID, TextureType.CUBEMAP, 0);

        GL30.glDrawArrays(GL30.GL_TRIANGLES, 0, vao.getVertexCount());

        Texture.unbind(TextureType.CUBEMAP, 0);
        GL30.glDepthMask(true);

        vao.unbind();
    }

    public int getTextureID() {
        return textureID;
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
