package org.rivetengine.debug;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.rivetengine.core.Game;
import org.rivetengine.debug.draw.Cube;
import org.rivetengine.debug.draw.Line;
import org.rivetengine.debug.shader.DebugShader;
import org.rivetengine.rendering.RenderUtils;
import org.rivetengine.toolkit.memory.Disposable;

public class DebugRenderer implements Disposable {
    public final DebugShader shader;

    public DebugRenderer(DebugShader shader) {
        this.shader = shader;
    }

    public void render(Game game) {
        shader.bind();

        Matrix4f[] cameraMatrices = RenderUtils.createCameraMatrices(game);
        shader.projectionMatrix.loadMatrix(cameraMatrices[0]);
        shader.viewMatrix.loadMatrix(cameraMatrices[1]);

        /*
         * // Position attribute
         * GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
         * GL20.glEnableVertexAttribArray(0);
         * 
         * // Color attribute (make sure the location matches the shader's location)
         * GL20.glVertexAttribPointer(1, 3, GL11.GL_FLOAT, false, 0, 0);
         * GL20.glEnableVertexAttribArray(1);
         */

        renderLines();
        renderCubes();

        shader.unbind();
    }

    private static void renderLines() {
        GL11.glBegin(GL11.GL_LINES);

        for (Line line : Debug.lines) {
            line.draw();
        }

        Debug.lines.clear();
        GL11.glEnd();
    }

    private static void renderCubes() {
        GL11.glBegin(GL11.GL_LINES);

        for (Cube cube : Debug.cubes) {
            cube.draw();
        }

        Debug.cubes.clear();
        GL11.glEnd();
    }

    @Override
    public void dispose() {
        shader.dispose();
    }
}
