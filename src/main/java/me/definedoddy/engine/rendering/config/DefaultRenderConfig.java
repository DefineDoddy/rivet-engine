package me.definedoddy.engine.rendering.config;

public class DefaultRenderConfig extends RenderConfig {
    @Override
    public int getMaxLights() {
        return 100;
    }

    @Override
    public float getAmbientLight() {
        return 0.1f;
    }

    @Override
    public boolean useMipmapping() {
        return true;
    }

    @Override
    public float getLODBias() {
        return -0.4f;
    }
}
