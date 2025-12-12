package org.rivetengine.file;

import java.util.HashMap;
import java.util.Map;

import me.definedoddy.engine.rendering.cubemap.CubeMap;
import me.definedoddy.engine.rendering.cubemap.CubeMapLoader;
import me.definedoddy.engine.rendering.mesh.Mesh;
import me.definedoddy.engine.rendering.texture.Material;
import me.definedoddy.toolkit.memory.Handle;

public class Assets {
    private static final Map<String, Object> storage = new HashMap<>();

    public static <T> Handle<T> load(String path, Class<T> type) {
        if (!storage.containsKey(path)) {
            T asset = loadFromDisk(path, type);
            storage.put(path, asset);
        }
        return new Handle<>(path);
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(Handle<T> handle) {
        return (T) storage.get(handle.getId());
    }

    @SuppressWarnings("unchecked")
    private static <T> T loadFromDisk(String path, Class<T> type) {
        if (type == Mesh.class) {
            return (T) MeshLoader.load(path);
        }
        if (type == Material.class) {
            return (T) MaterialLoader.load(path);
        }
        if (type == CubeMap.class) {
            return (T) CubeMapLoader.load(path);
        }

        throw new IllegalArgumentException("Unknown asset type: " + type);
    }

    public static boolean has(String id) {
        return storage.containsKey(id);
    }

    public static <T> Handle<T> register(String id, T asset) {
        storage.put(id, asset);
        return new Handle<>(id);
    }
}
