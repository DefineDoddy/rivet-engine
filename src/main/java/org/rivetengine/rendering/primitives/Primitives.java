package org.rivetengine.rendering.primitives;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.rivetengine.core.Assets;
import org.rivetengine.rendering.mesh.Mesh;
import org.rivetengine.toolkit.memory.Handle;

public class Primitives {
    public static Handle<Mesh> quad(Vector2f size) {
        String id = "quad_" + size.x + "_" + size.y;

        if (Assets.has(id)) {
            return new Handle<>(id);
        }

        return Assets.register(id, new Quad(size));
    }

    public static Handle<Mesh> cube(Vector3f size) {
        String id = "cube_" + size.x + "_" + size.y + "_" + size.z;

        if (Assets.has(id)) {
            return new Handle<>(id);
        }

        return Assets.register(id, new Cube(size));
    }
}
