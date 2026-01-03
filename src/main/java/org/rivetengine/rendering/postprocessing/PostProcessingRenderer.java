package org.rivetengine.rendering.postprocessing;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.rivetengine.core.Game;
import org.rivetengine.entity.Entity;
import org.rivetengine.entity.components.camera.PostProcessing;
import org.rivetengine.rendering.Framebuffer;
import org.rivetengine.rendering.RenderUtils;
import org.rivetengine.rendering.mesh.object.Vao;
import org.rivetengine.rendering.postprocessing.bloom.Bloom;
import org.rivetengine.rendering.postprocessing.bloom.BloomShader;
import org.rivetengine.rendering.postprocessing.chromaticaberration.ChromaticAberration;
import org.rivetengine.rendering.postprocessing.chromaticaberration.ChromaticAberrationShader;
import org.rivetengine.rendering.postprocessing.colourgrading.ColourGrading;
import org.rivetengine.rendering.postprocessing.colourgrading.ColourGradingShader;
import org.rivetengine.rendering.postprocessing.shader.*;
import org.rivetengine.rendering.postprocessing.vignette.Vignette;
import org.rivetengine.rendering.postprocessing.vignette.VignetteShader;
import org.rivetengine.toolkit.buffer.BufferUtils;
import org.rivetengine.toolkit.memory.Disposable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostProcessingRenderer implements Disposable {
    private final Game game;
    private final Framebuffer framebuffer;
    private final Framebuffer framebuffer2;
    private final Vao quad;

    private final Map<Class<? extends PostProcessEffect>, PostProcessShader> shaders = new HashMap<>();

    public PostProcessingRenderer(Game game) {
        this.game = game;
        this.framebuffer = new Framebuffer(game.window.getWidth(), game.window.getHeight());
        this.framebuffer2 = new Framebuffer(game.window.getWidth(), game.window.getHeight());
        this.quad = createQuad();

        shaders.put(Bloom.class, BloomShader.create());
        shaders.put(ChromaticAberration.class, ChromaticAberrationShader.create());
        shaders.put(ColourGrading.class, ColourGradingShader.create());
        shaders.put(Vignette.class, VignetteShader.create());
    }

    public void render(int textureId) {
        if (game.window.getWidth() != framebuffer.getWidth() || game.window.getHeight() != framebuffer.getHeight()) {
            if (game.window.getWidth() > 0 && game.window.getHeight() > 0) {
                framebuffer.resize(game.window.getWidth(), game.window.getHeight());
                framebuffer2.resize(game.window.getWidth(), game.window.getHeight());
            }
        }

        List<PostProcessEffect> effects = getActiveEffects();

        GL11.glDisable(GL11.GL_DEPTH_TEST);
        quad.bind();
        GL20.glEnableVertexAttribArray(0);

        int currentTexture = textureId;
        Framebuffer currentFbo = framebuffer;
        Framebuffer targetFbo = framebuffer2;

        for (int i = 0; i < effects.size(); i++) {
            PostProcessEffect effect = effects.get(i);
            PostProcessShader shader = shaders.get(effect.getClass());

            if (shader == null) {
                continue;
            }

            boolean last = (i == effects.size() - 1);

            if (last) {
                GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
                GL11.glViewport(0, 0, game.window.getWidth(), game.window.getHeight());
            } else {
                targetFbo.bind();
            }

            shader.bind();
            setUniforms(shader, effect);

            GL20.glActiveTexture(GL20.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, currentTexture);

            GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
            shader.unbind();

            if (!last) {
                currentTexture = targetFbo.getTextureId();
                Framebuffer temp = currentFbo;
                currentFbo = targetFbo;
                targetFbo = temp;
            }
        }

        GL20.glDisableVertexAttribArray(0);
        quad.unbind();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    public List<PostProcessEffect> getActiveEffects() {
        Entity camera = RenderUtils.getActiveCameraEntity(game);
        List<PostProcessEffect> effects = new ArrayList<>();
        if (camera != null && camera.hasComponent(PostProcessing.class)) {
            PostProcessing postProcessing = camera.getComponent(PostProcessing.class);
            for (PostProcessEffect effect : postProcessing.getEffects()) {
                if (effect.enabled) {
                    effects.add(effect);
                }
            }
        }
        return effects;
    }

    private void setUniforms(PostProcessShader shader, PostProcessEffect effect) {
        if (effect instanceof Bloom bloom) {
            BloomShader s = (BloomShader) shader;
            s.threshold.loadFloat(bloom.threshold);
            s.intensity.loadFloat(bloom.intensity);
        } else if (effect instanceof ChromaticAberration chromaticAberration) {
            ChromaticAberrationShader s = (ChromaticAberrationShader) shader;
            s.intensity.loadFloat(chromaticAberration.intensity);
        } else if (effect instanceof ColourGrading colourGrading) {
            ColourGradingShader s = (ColourGradingShader) shader;
            s.exposure.loadFloat(colourGrading.exposure);
            s.contrast.loadFloat(colourGrading.contrast);
            s.saturation.loadFloat(colourGrading.saturation);
        } else if (effect instanceof Vignette vignette) {
            VignetteShader s = (VignetteShader) shader;
            s.intensity.loadFloat(vignette.intensity);
            s.smoothness.loadFloat(vignette.smoothness);
        }
    }

    public void bindFramebuffer() {
        framebuffer.bind();
    }

    public void unbindFramebuffer() {
        framebuffer.unbind();
    }

    public int getOutputTextureId() {
        return framebuffer.getTextureId();
    }

    private Vao createQuad() {
        Vao vao = new Vao();
        float[] positions = { -1, 1, -1, -1, 1, 1, 1, -1 };
        vao.storeData(0, BufferUtils.createFloatBuffer(positions), 2);
        return vao;
    }

    @Override
    public void dispose() {
        for (PostProcessShader shader : shaders.values()) {
            shader.dispose();
        }
        framebuffer.dispose();
        framebuffer2.dispose();
        quad.dispose();
    }
}
