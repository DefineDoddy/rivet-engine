package me.definedoddy.engine.rendering.mesh.obj.importer;

import me.definedoddy.engine.rendering.texture.*;
import me.definedoddy.toolkit.file.File;

import java.awt.*;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

public class MaterialImporter {
    private final File file;
    private final BufferedReader reader;
    private final Map<String, Material> materials = new HashMap<>();

    public MaterialImporter(File file) {
        this.file = file;
        this.reader = file.getReader();
    }

    public static Map<String, Material> importMaterials(File file) {
        MaterialImporter importer = new MaterialImporter(file);
        return importer.importMaterials();
    }

    public Map<String, Material> importMaterials() {
        if (!materials.isEmpty())
            return materials;

        if (reader == null) {
            throw new RuntimeException("Reader is null");
        }

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("newmtl ")) {
                    parseMaterial(line);
                }
            }

            reader.close();
        } catch (Exception e) {
            throw new RuntimeException("Failed to import materials", e);
        }

        return materials;
    }

    public Map<String, Material> getMaterials() {
        return materials;
    }

    private void parseMaterial(String startLine) {
        String name = startLine.split(" ")[1];
        MaterialBuilder builder = new MaterialBuilder();

        // Set material properties
        String line;
        try {
            while (true) {
                line = reader.readLine();
                if (line == null || line.startsWith("newmtl "))
                    break;
                String[] parts = line.split(" ");

                if (line.startsWith("Kd ")) {
                    builder.colour(new Color(Float.parseFloat(parts[1]), Float.parseFloat(parts[2]),
                            Float.parseFloat(parts[3])));
                } else if (line.startsWith("Ns ")) {
                    builder.shininess(Float.parseFloat(parts[1]));
                } else if (line.startsWith("d ")) {
                    if (Float.parseFloat(parts[1]) > 0.5)
                        builder.transparent();
                } else if (line.startsWith("map_Kd ")) {
                    builder.diffuse(loadTexture(parts[1], TextureType.DIFFUSE));
                } else if (line.startsWith("map_Ks ")) {
                    builder.specular(loadTexture(parts[1], TextureType.SPECULAR));
                } else if (line.startsWith("map_Ns ")) {
                    builder.normal(loadTexture(parts[1], TextureType.NORMAL));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse material properties", e);
        }

        materials.put(name, builder.build());
    }

    private Texture loadTexture(String file, TextureType type) {
        File texFile = new File(this.file.getParent(), file);
        return TextureLoader.loadTexture2D(texFile, type);
    }
}
