package me.definedoddy.engine.rendering.object;

import me.definedoddy.toolkit.debug.DebugLog;
import me.definedoddy.toolkit.memory.Disposable;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class Mesh implements Disposable {
    private final Vao vao;
    private Color color = Color.WHITE;

    public Mesh(Vao vao) {
        this.vao = vao;
    }

    public void render() {
        vao.bind();
        DebugLog.info("Rendering mesh with " + vao.getVertexCount() + " vertices");
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vao.getVertexCount());
        vao.unbind();
    }

    @Override
    public void dispose() {
        vao.dispose();
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
