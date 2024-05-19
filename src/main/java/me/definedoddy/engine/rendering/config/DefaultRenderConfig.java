package me.definedoddy.engine.rendering.config;

public class DefaultRenderConfig extends RenderConfig {
    @Override
    public int getMaxLightsOnMesh() {
        return 4;
    }

    @Override
    public float getAmbientLight() {
        return 0.1f;
    }
}
