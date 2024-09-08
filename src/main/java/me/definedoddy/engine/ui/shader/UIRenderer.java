package me.definedoddy.engine.ui.shader;

import me.definedoddy.engine.rendering.object.Vao;
import me.definedoddy.engine.ui.UI;
import me.definedoddy.engine.ui.UIBlock;
import me.definedoddy.toolkit.buffer.BufferUtils;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class UIRenderer {
    private static final float[] POSITIONS = {
            -1, 1,
            -1, -1,
            1, 1,
            1, 1,
            -1, -1,
            1, -1
    };

    private final UIShader shader;
    private final Vao vao = new Vao();

    private final List<UIBlock> blocks = new ArrayList<>();

    public UIRenderer(UIShader shader) {
        this.shader = shader;
        vao.storeData(0, BufferUtils.createFloatBuffer(POSITIONS), 2);
    }

    public void render() {
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        shader.bind();
        vao.bind();

        shader.getUIDimensions().loadVec2(new Vector2f(UI.getWidth(), UI.getHeight()));

        for (UIBlock block : blocks) {
            shader.getTransform().loadVec4(new Vector4f(
                    block.getPosition().x,
                    block.getPosition().y,
                    block.getSize().x,
                    block.getSize().y
            ));

            GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 6);
        }

        vao.unbind();
        shader.unbind();

        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    public void addBlock(UIBlock block) {
        blocks.add(block);
    }

    public void removeBlock(UIBlock block) {
        blocks.remove(block);
    }

    public void stop() {
        shader.dispose();
        vao.dispose();
    }
}
