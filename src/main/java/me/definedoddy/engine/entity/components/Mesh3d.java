package me.definedoddy.engine.entity.components;

import me.definedoddy.engine.entity.Component;
import me.definedoddy.engine.rendering.mesh.Mesh;
import me.definedoddy.toolkit.memory.Handle;

public class Mesh3d implements Component {
    private final Handle<Mesh> mesh;

    public Mesh3d(Handle<Mesh> mesh) {
        this.mesh = mesh;
    }
}
