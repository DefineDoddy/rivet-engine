package org.rivetengine.entity.components.rendering;

import org.rivetengine.entity.component.Component;
import org.rivetengine.rendering.cubemap.CubeMap;
import org.rivetengine.toolkit.memory.Handle;

public class Skybox implements Component {
    public Handle<CubeMap> cubeMap;
    public float brightness;
    public float rotation;

    public Skybox(Handle<CubeMap> cubeMap) {
        this(cubeMap, 1f);
    }

    public Skybox(Handle<CubeMap> cubeMap, float brightness) {
        this.cubeMap = cubeMap;
        this.brightness = brightness;
    }
}
