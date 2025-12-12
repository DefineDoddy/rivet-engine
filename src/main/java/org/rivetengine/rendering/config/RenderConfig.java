package org.rivetengine.rendering.config;

public abstract class RenderConfig {
    public abstract int getMaxLights();
    public abstract float getAmbientLight();
    public abstract boolean useMipmapping();
    public abstract float getLODBias();
}
