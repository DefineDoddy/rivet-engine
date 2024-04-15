package me.definedoddy.engine.rendering.object;

import me.definedoddy.engine.rendering.texture.Texture;
import me.definedoddy.toolkit.buffer.BufferUtils;
import me.definedoddy.toolkit.memory.Disposable;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.awt.*;
import java.nio.FloatBuffer;

public class Mesh implements Disposable {
    private final Vao vao = new Vao();
    private Color colour = Color.WHITE;
    private Texture texture;

    public void render() {
        vao.bind();
        GL20.glBindTexture(GL11.GL_TEXTURE_2D, texture.getId());
        GL11.glDrawElements(GL11.GL_TRIANGLES, vao.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        vao.unbind();
    }

    public void setVertexPositions(float[] vertPositions) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertPositions);

        vao.bind();
        vao.storeData(0, buffer, 3);
        vao.unbind();
    }

    public void setTextureCoords(float[] texCoords) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(texCoords);

        vao.bind();
        vao.storeData(1, buffer, 2);
        vao.unbind();
    }

    public void setNormals(float[] normals) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(normals);

        vao.bind();
        vao.storeData(2, buffer, 3);
        vao.unbind();
    }

    public void setIndices(int[] indices) {
        vao.bind();
        vao.storeIndices(BufferUtils.createIntBuffer(indices));
        vao.unbind();
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    @Override
    public void dispose() {
        vao.dispose();
    }

    public void setColour(Color colour) {
        this.colour = colour;
    }

    public Color getColour() {
        return colour;
    }
}
