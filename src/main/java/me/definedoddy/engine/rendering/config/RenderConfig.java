package me.definedoddy.engine.rendering.config;

public abstract class RenderConfig {
    public abstract int getMaxDirectionalLights();
    public abstract float getAmbientLight();
    public abstract boolean useMipmapping();
    public abstract float getLODBias();
}
