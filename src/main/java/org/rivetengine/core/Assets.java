package org.rivetengine.core;

import java.util.HashMap;
import java.util.Map;

import org.rivetengine.rendering.cubemap.CubeMap;
import org.rivetengine.rendering.cubemap.CubeMapLoader;
import org.rivetengine.toolkit.file.File;
import org.rivetengine.rendering.sprite.Icon;
import org.rivetengine.toolkit.memory.Handle;

public class Assets {
    private static final Map<String, Object> storage = new HashMap<>();

    public static <T> Handle<T> load(String path, Class<T> type) {
        if (!storage.containsKey(path)) {
            if (new File(path).exists()) {
                T asset = loadFromDisk(path, type);
                storage.put(path, asset);
                return new Handle<>(path);
            }
            return null;
        }

        return new Handle<>(path);
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(Handle<T> handle) {
        return (T) storage.get(handle.id);
    }

    @SuppressWarnings("unchecked")
    private static <T> T loadFromDisk(String path, Class<T> type) {
        if (type == CubeMap.class) {
            return (T) CubeMapLoader.load(path);
        }
        if (type == Icon.class) {
            return (T) Icon.fromFile(new File(path));
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
