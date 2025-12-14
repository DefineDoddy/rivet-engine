package org.rivetengine.rendering.mesh.obj;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.rivetengine.core.Assets;
import org.rivetengine.entity.Entity;
import org.rivetengine.entity.components.Name;
import org.rivetengine.entity.components.Transform;
import org.rivetengine.entity.components.rendering.Material;
import org.rivetengine.entity.components.rendering.Mesh3d;
import org.rivetengine.rendering.mesh.Mesh;
import org.rivetengine.toolkit.file.File;
import org.rivetengine.toolkit.memory.Handle;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjImporter {
    private final File file;
    private BufferedReader reader;

    private final List<Vector3f> positions = new ArrayList<>();
    private final List<Vector2f> texCoords = new ArrayList<>();
    private final List<Vector3f> normals = new ArrayList<>();

    private final Map<String, Material> materials = new HashMap<>();
    private final Map<String, Integer> vertexCache = new HashMap<>();

    private Entity root;
    private Entity currentEntity;
    private MeshBuilder currentMesh;
    private String currentMeshName;
    private Material currentMaterial;

    private ObjImporter(File file) {
        this.file = file;
    }

    public static Entity load(File file) {
        ObjImporter importer = new ObjImporter(file);
        return importer.parse();
    }

    private Entity parse() {
        String name = file.getNameWithoutExtension();

        root = new Entity();
        root.addComponent(new Name(name));
        root.addComponent(new Transform());

        loadMaterials();
        loadMeshes();

        return root;
    }

    private void loadMaterials() {
        reader = file.getReader();

        try {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("mtllib ")) {
                    String mtlName = line.substring(7).trim();
                    File mtlFile = new File(file.getParent(), mtlName);
                    materials.putAll(MtlImporter.load(mtlFile));
                }
            }
            reader.close();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load materials: " + file, e);
        }
    }

    private void loadMeshes() {
        reader = file.getReader();
        currentMaterial = new Material();

        try {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty() || line.startsWith("#"))
                    continue;

                if (line.startsWith("o ")) {
                    finishCurrentMesh();
                    startMesh(line.substring(2).trim());
                } else if (line.startsWith("v ")) {
                    positions.add(parseVec3(line));
                } else if (line.startsWith("vt ")) {
                    texCoords.add(parseVec2(line));
                } else if (line.startsWith("vn ")) {
                    normals.add(parseVec3(line));
                } else if (line.startsWith("usemtl ")) {
                    currentMaterial = materials.getOrDefault(
                            line.substring(7).trim(),
                            new Material());
                } else if (line.startsWith("f ")) {
                    if (currentMesh == null) {
                        startMesh("default");
                    }
                    parseFace(line);
                }
            }

            finishCurrentMesh();
            reader.close();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load meshes: " + file, e);
        }
    }

    private void startMesh(String name) {
        currentEntity = new Entity();
        currentEntity.addComponent(new Name(name));
        currentEntity.addComponent(new Transform());

        currentMesh = new MeshBuilder();
        currentMeshName = name;

        vertexCache.clear();
    }

    private void finishCurrentMesh() {
        if (currentMesh == null || currentMesh.isEmpty()) {
            return;
        }

        Mesh mesh = currentMesh.build();
        Handle<Mesh> handle = Assets.register(currentMeshName, mesh);

        currentEntity.addComponent(new Mesh3d(handle));
        currentEntity.addComponent(currentMaterial);
        root.addChild(currentEntity);

        currentEntity = null;
        currentMesh = null;
        currentMeshName = null;
    }

    private void parseFace(String line) {
        String[] parts = line.substring(2).trim().split("\\s+");
        int[] indices = new int[parts.length];

        for (int i = 0; i < parts.length; i++) {
            indices[i] = processVertex(parts[i]);
        }

        // Triangulate (fan)
        for (int i = 1; i < indices.length - 1; i++) {
            currentMesh.addIndex(indices[0]);
            currentMesh.addIndex(indices[i]);
            currentMesh.addIndex(indices[i + 1]);
        }
    }

    private int processVertex(String vertexId) {
        if (vertexCache.containsKey(vertexId))
            return vertexCache.get(vertexId);

        String[] parts = vertexId.split("/");

        int posIdx = Integer.parseInt(parts[0]) - 1;
        Vector3f position = positions.get(posIdx);

        Vector2f texCoord = new Vector2f();
        if (parts.length > 1 && !parts[1].isEmpty()) {
            int texIdx = Integer.parseInt(parts[1]) - 1;
            texCoord = texCoords.get(texIdx);
        }

        Vector3f normal = new Vector3f();
        if (parts.length > 2 && !parts[2].isEmpty()) {
            int normIdx = Integer.parseInt(parts[2]) - 1;
            normal = normals.get(normIdx);
        }

        int index = currentMesh.addVertex(position, texCoord, normal);
        vertexCache.put(vertexId, index);
        return index;
    }

    private Vector3f parseVec3(String line) {
        String[] parts = line.split("\\s+");
        return new Vector3f(
                Float.parseFloat(parts[1]),
                Float.parseFloat(parts[2]),
                Float.parseFloat(parts[3]));
    }

    private Vector2f parseVec2(String line) {
        String[] parts = line.split("\\s+");
        return new Vector2f(
                Float.parseFloat(parts[1]),
                1f - Float.parseFloat(parts[2]) // flip Y for OpenGL
        );
    }
}