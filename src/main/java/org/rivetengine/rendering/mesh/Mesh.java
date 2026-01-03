package org.rivetengine.rendering.mesh;

import org.rivetengine.toolkit.buffer.BufferUtils;
import org.rivetengine.toolkit.memory.Disposable;
import org.rivetengine.rendering.mesh.object.Vao;
import org.rivetengine.physics.collision.BoundingBox;

import org.lwjgl.opengl.GL11;

public class Mesh implements Disposable {
    private final Vao vao;
    private final int indexCount;
    private final BoundingBox bounds;

    public Mesh(float[] positions, float[] texCoords, float[] normals, int[] indices, BoundingBox bounds) {
        this.vao = new Vao();
        this.indexCount = indices.length;
        this.bounds = bounds;

        vao.storeData(0, BufferUtils.createFloatBuffer(positions), 3);
        vao.storeData(1, BufferUtils.createFloatBuffer(texCoords), 2);
        vao.storeData(2, BufferUtils.createFloatBuffer(normals), 3);
        vao.storeIndices(BufferUtils.createIntBuffer(indices));
    }

    public void render() {
        vao.bind();
        GL11.glDrawElements(GL11.GL_TRIANGLES, indexCount, GL11.GL_UNSIGNED_INT, 0);
        vao.unbind();
    }

    public void bind() {
        vao.bind();
    }

    public void unbind() {
        vao.unbind();
    }

    public int getIndexCount() {
        return indexCount;
    }

    public BoundingBox getBounds() {
        return bounds;
    }

    @Override
    public void dispose() {
        vao.dispose();
    }
}