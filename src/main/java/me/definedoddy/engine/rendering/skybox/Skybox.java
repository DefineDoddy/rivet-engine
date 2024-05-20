package me.definedoddy.engine.rendering.skybox;

import me.definedoddy.engine.rendering.cubemap.CubeMap;
import me.definedoddy.toolkit.memory.Disposable;

public class Skybox implements Disposable {
    private final CubeMap cubemap;
    private float rotation;

    public Skybox(CubeMap cubemap) {
        this.cubemap = cubemap;
    }

    public CubeMap getCubemap() {
        return cubemap;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    @Override
    public void dispose() {
        cubemap.dispose();
    }
}
