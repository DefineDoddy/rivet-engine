package me.definedoddy.engine.rendering.shader.custom;

import me.definedoddy.engine.manager.GameManager;
import me.definedoddy.engine.rendering.lighting.DirectionalLight;
import me.definedoddy.engine.rendering.shader.Uniform;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

public class UniformDirectionalLights extends Uniform {
    private static final int LIGHT_DATA_SIZE = calcLightDataSize();

    private final int blockBinding;
    private final int uboId;

    public UniformDirectionalLights(String name, int blockBinding) {
        super(name);
        this.blockBinding = blockBinding;
        uboId = GL15.glGenBuffers();
    }

    public void loadDirectionalLights(DirectionalLight[] lights) {
        int maxLights = GameManager.getRenderEngine().getRenderConfig().getMaxDirectionalLights();

        GL15.glBindBuffer(GL31.GL_UNIFORM_BUFFER, uboId);
        GL15.glBufferData(GL31.GL_UNIFORM_BUFFER, (long) LIGHT_DATA_SIZE * maxLights, GL15.GL_DYNAMIC_DRAW);
        GL15.glBindBuffer(GL31.GL_UNIFORM_BUFFER, 0);

        // Get the block index and bind it
        int blockIndex = GL31.glGetUniformBlockIndex(getProgramID(), getName());
        GL31.glUniformBlockBinding(getProgramID(), blockIndex, blockBinding);

        // Bind the UBO to the binding point
        GL30.glBindBufferBase(GL31.GL_UNIFORM_BUFFER, blockBinding, uboId);
        GL15.glBindBuffer(GL31.GL_UNIFORM_BUFFER, uboId);

        ByteBuffer buffer = MemoryUtil.memAlloc(LIGHT_DATA_SIZE * maxLights);
        FloatBuffer floatBuffer = buffer.asFloatBuffer();

        for (int i = 0; i < lights.length && i < maxLights; i++) {
            DirectionalLight light = lights[i];

            // Store the ambient color
            floatBuffer.put(light.getAmbientColour().getRed() / 255f);
            floatBuffer.put(light.getAmbientColour().getGreen() / 255f);
            floatBuffer.put(light.getAmbientColour().getBlue() / 255f);
            floatBuffer.put(0);

            // Store the diffuse color
            floatBuffer.put(light.getDiffuseColour().getRed() / 255f);
            floatBuffer.put(light.getDiffuseColour().getGreen() / 255f);
            floatBuffer.put(light.getDiffuseColour().getBlue() / 255f);
            floatBuffer.put(0);

            // Store the specular color
            floatBuffer.put(light.getSpecularColour().getRed() / 255f);
            floatBuffer.put(light.getSpecularColour().getGreen() / 255f);
            floatBuffer.put(light.getSpecularColour().getBlue() / 255f);
            floatBuffer.put(0);

            // Store the direction
            floatBuffer.put(light.getDirection().x).put(light.getDirection().y).put(light.getDirection().z);
            floatBuffer.put(0);
        }

        floatBuffer.flip();
        GL15.glBufferSubData(GL31.GL_UNIFORM_BUFFER, 0, floatBuffer);
        GL15.glBindBuffer(GL31.GL_UNIFORM_BUFFER, 0);

        MemoryUtil.memFree(buffer);
    }

    private static int calcLightDataSize() {
        return Float.BYTES * 12;
    }
}
