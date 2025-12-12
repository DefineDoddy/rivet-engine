package org.rivetengine.entity.components.rendering;

import org.rivetengine.entity.component.Component;
import org.rivetengine.rendering.mesh.Mesh;
import org.rivetengine.toolkit.memory.Handle;

public class Mesh3d implements Component {
    private final Handle<Mesh> mesh;

    public Mesh3d(Handle<Mesh> mesh) {
        this.mesh = mesh;
    }

    public Handle<Mesh> getMesh() {
        return mesh;
    }
}
