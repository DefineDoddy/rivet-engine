package me.definedoddy.engine.rendering.model.model;

import me.definedoddy.engine.rendering.model.mesh.Mesh;
import me.definedoddy.engine.rendering.texture.Material;

import java.util.HashMap;
import java.util.Map;

public class MeshMap {
    private final Map<Mesh, Material> data;
    private final Map<String, Mesh> meshIdMap;

    public MeshMap(Mesh mesh) {
        this(Map.of(mesh, new Material()));
    }

    public MeshMap(Map<Mesh, Material> data) {
        this.data = new HashMap<>(data);
        meshIdMap = new HashMap<>();

        for (Mesh mesh : data.keySet()) {
            meshIdMap.put(mesh.getId(), mesh);
        }
    }

    public Map<Mesh, Material> getData() {
        return data;
    }

    public Map.Entry<Mesh, Material> get(String meshId) {
        Mesh mesh = meshIdMap.get(meshId);
        if (mesh == null) return null;

        return Map.entry(mesh, data.get(mesh));
    }

    public int size() {
        return data.size();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MeshMap other)) return false;
        if (data.size() != other.data.size()) return false;

        for (Map.Entry<Mesh, Material> entry : data.entrySet()) {
            if (!other.data.containsKey(entry.getKey()) || !other.data.get(entry.getKey()).equals(entry.getValue())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public MeshMap clone() {
        return new MeshMap(data);
    }
}
