package org.rivetengine.rendering;

import org.rivetengine.core.Assets;
import org.rivetengine.rendering.mesh.Mesh;
import org.rivetengine.rendering.mesh.MeshBuilder;
import org.rivetengine.toolkit.memory.Handle;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Primitives {
    public static Handle<Mesh> cube(Vector3f size) {
        String handleId = "cube_" + size.x + "x" + size.y + "x" + size.z;
        Handle<Mesh> existing = Assets.load(handleId, Mesh.class);

        if (existing != null) {
            return existing;
        }

        MeshBuilder builder = new MeshBuilder();

        float hw = size.x / 2f;
        float hh = size.y / 2f;
        float hd = size.z / 2f;

        Vector3f[] positions = {
                new Vector3f(hw, hh, -hd),
                new Vector3f(hw, -hh, -hd),
                new Vector3f(hw, hh, hd),
                new Vector3f(hw, -hh, hd),
                new Vector3f(-hw, hh, -hd),
                new Vector3f(-hw, -hh, -hd),
                new Vector3f(-hw, hh, hd),
                new Vector3f(-hw, -hh, hd),
        };

        Vector3f[] normals = {
                new Vector3f(0f, 1f, 0f),
                new Vector3f(0f, 0f, 1f),
                new Vector3f(-1f, 0f, 0f),
                new Vector3f(0f, -1f, 0f),
                new Vector3f(1f, 0f, 0f),
                new Vector3f(0f, 0f, -1f)
        };

        Vector2f[] texCoords = {
                new Vector2f(0, 0),
                new Vector2f(0, 1),
                new Vector2f(1, 1),
                new Vector2f(1, 0),
        };

        for (int i = 0; i < 8; i++) {
            builder.addVertex(positions[i], texCoords[i % 4], normals[i / 4]);
        }

        for (int index : CUBE_INDICES) {
            builder.addIndex(index);
        }

        return Assets.register(handleId, builder.build());
    }

    public static Handle<Mesh> quad(Vector2f size) {
        String handleId = "quad_" + size.x + "x" + size.y;
        Handle<Mesh> existing = Assets.load(handleId, Mesh.class);

        if (existing != null) {
            return existing;
        }

        MeshBuilder builder = new MeshBuilder();

        float hw = size.x / 2f;
        float hh = size.y / 2f;

        Vector3f[] positions = {
                new Vector3f(-hw, 0, hh),
                new Vector3f(hw, 0, hh),
                new Vector3f(-hw, 0, -hh),
                new Vector3f(hw, 0, -hh)
        };

        Vector3f normal = new Vector3f(0f, 1f, 0f);

        Vector2f[] texCoords = {
                new Vector2f(0, 0),
                new Vector2f(1, 0),
                new Vector2f(0, 1),
                new Vector2f(1, 1)
        };

        for (int i = 0; i < 4; i++) {
            builder.addVertex(positions[i], texCoords[i], normal);
        }

        for (int index : QUAD_INDICES) {
            builder.addIndex(index);
        }

        return Assets.register(handleId, builder.build());
    }

    private static final int[] CUBE_INDICES = {
            1, 5, 7, 3, 4, 3, 7, 8, 8, 7, 5, 6, 6, 2, 4, 8, 2, 1, 3, 4, 6, 5, 1, 2
    };

    private static final int[] QUAD_INDICES = {
            0, 1, 2, 1, 3, 2
    };
}