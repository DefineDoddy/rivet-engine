package me.definedoddy.engine.entity.components;

import me.definedoddy.engine.entity.Component;
import me.definedoddy.engine.rendering.cubemap.CubeMap;
import me.definedoddy.toolkit.memory.Handle;

public class Skybox implements Component {
    private Handle<CubeMap> cubeMap;
    private float brightness;
    private float rotation;

    public Skybox(Handle<CubeMap> cubeMap) {
        this(cubeMap, 1f);
    }

    public Skybox(Handle<CubeMap> cubeMap, float brightness) {
        this.cubeMap = cubeMap;
        this.brightness = brightness;
    }

    public Handle<CubeMap> getCubeMap() {
        return cubeMap;
    }

    public void setCubeMap(Handle<CubeMap> cubeMap) {
        this.cubeMap = cubeMap;
    }

    public float getBrightness() {
        return brightness;
    }

    public void setBrightness(float brightness) {
        this.brightness = brightness;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
}
