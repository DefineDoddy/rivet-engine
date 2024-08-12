package me.definedoddy.toolkit.model.obj.importer;

import me.definedoddy.engine.rendering.object.mesh.Mesh;
import me.definedoddy.engine.rendering.object.model.Model;
import me.definedoddy.engine.rendering.texture.Material;
import me.definedoddy.toolkit.file.File;
import me.definedoddy.toolkit.model.obj.Vertex;
import me.definedoddy.toolkit.model.obj.VertexData;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

public class ModelImporter {
    private final File file;
    private BufferedReader reader;

    private final VertexData vertexData = new VertexData();
    private final Map<String, Material> materials = new HashMap<>();
    private Mesh currentMesh;

    private final Map<Mesh, Material> meshData = new HashMap<>();

    public ModelImporter(File file) {
        this.file = file;
        this.reader = file.getReader();
    }

    public static Model importModel(File file) {
        return importModel(file, new Material());
    }

    public static Model importModel(File file, Material material) {
        ModelImporter importer = new ModelImporter(file);
        importer.importMaterials();
        importer.importMeshes();
        return new Model(importer.meshData, material);
    }

    private void importMaterials() {
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("mtllib")) {
                    String fileName = line.split(" ")[1];
                    File mtlFile = new File(file.getParent(), fileName);
                    materials.putAll(MaterialImporter.importMaterials(mtlFile));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to import materials", e);
        }
    }

    private void importMeshes() {
        reader = file.getReader();

        try {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("o ")) {
                    startNewMesh(line);
                } else if (line.startsWith("v ")) {
                    vertexData.positions.add(parseVector3f(line));
                } else if (line.startsWith("vt ")) {
                    vertexData.textureCoords.add(parseTexCoords(line));
                } else if (line.startsWith("vn ")) {
                    vertexData.normals.add(parseVector3f(line));
                } else if (line.startsWith("usemtl ")) {
                    setMeshMaterial(line);
                } else if (line.startsWith("f ")) {
                    processFace(line);
                }
            }

            if (currentMesh != null) currentMesh.process();
        } catch (Exception e) {
            throw new RuntimeException("Failed to read mesh data", e);
        }
    }

    private void startNewMesh(String line) {
        if (currentMesh != null) currentMesh.process();
        //vertexData.clear();

        String objectName = line.split(" ")[1];
        int offset = currentMesh == null ? 0 : currentMesh.getVertexCount();
        currentMesh = new Mesh(objectName, offset);

        meshData.put(currentMesh, new Material());
    }

    private void setMeshMaterial(String line) {
        String materialName = line.split(" ")[1];
        Material material = materials.getOrDefault(materialName, new Material());
        meshData.put(currentMesh, material);
    }

    private void processFace(String line) {
        String[] vertices = line.split(" ");
        Vertex[] vertexArray = new Vertex[vertices.length - 1];

        for (int i = 1; i < vertices.length; i++) {
            vertexArray[i - 1] = processVertex(vertices[i]);
            currentMesh.addVertex(vertexArray[i - 1]);
        }

        for (int i = 1; i < vertexArray.length - 1; i++) {
            currentMesh.addIndex(vertexArray[0].getIndex());
            currentMesh.addIndex(vertexArray[i].getIndex());
            currentMesh.addIndex(vertexArray[i + 1].getIndex());
        }
    }

    private Vertex processVertex(String vertexId) {
        String[] values = vertexId.split("/");

        int index = currentMesh.getVertexCount();
        Vector3f position = vertexData.positions.get(Integer.parseInt(values[0]) - 1);
        Vector2f textureCoord = vertexData.textureCoords.get(Integer.parseInt(values[1]) - 1);
        Vector3f normal = vertexData.normals.get(Integer.parseInt(values[2]) - 1);

        return new Vertex(index, position, textureCoord, normal);
    }

    private Vector3f parseVector3f(String line) {
        String[] values = line.split(" ");
        float x = Float.parseFloat(values[1]);
        float y = Float.parseFloat(values[2]);
        float z = Float.parseFloat(values[3]);
        return new Vector3f(x, y, z);
    }

    private Vector2f parseTexCoords(String line) {
        String[] values = line.split(" ");
        float x = Float.parseFloat(values[1]);
        float y = 1 - Float.parseFloat(values[2]);
        return new Vector2f(x, y);
    }
}
