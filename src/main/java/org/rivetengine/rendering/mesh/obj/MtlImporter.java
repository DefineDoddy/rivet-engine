package org.rivetengine.rendering.mesh.obj;

import org.rivetengine.entity.components.rendering.Material;
import org.rivetengine.toolkit.file.File;

import java.awt.Color;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

public class MtlImporter {
    public static Map<String, Material> load(File file) {
        Map<String, Material> materials = new HashMap<>();
        BufferedReader reader = file.getReader();

        try {
            String line;
            String currentName = null;
            Material currentMat = null;

            while ((line = reader.readLine()) != null) {
                if (line.isEmpty() || line.startsWith("#"))
                    continue;

                if (line.startsWith("newmtl ")) {
                    if (currentName != null)
                        materials.put(currentName, currentMat);

                    currentName = line.substring(7).trim();
                    currentMat = new Material();
                } else if (currentMat != null) {
                    if (line.startsWith("Kd ")) {
                        currentMat.diffuse = parseColour(line);
                    } else if (line.startsWith("Ks ")) {
                        currentMat.specular = parseColour(line);
                    } else if (line.startsWith("Ns ")) {
                        currentMat.shininess = Float.parseFloat(line.split("\\s+")[1]);
                    } else if (line.startsWith("map_Kd ")) {
                        currentMat.diffuseMap = line.substring(7).trim();
                    }
                }
            }

            if (currentName != null)
                materials.put(currentName, currentMat);

            reader.close();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load materials: " + file, e);
        }

        return materials;
    }

    private static Color parseColour(String line) {
        String[] parts = line.split("\\s+");
        return new Color(
                Float.parseFloat(parts[1]),
                Float.parseFloat(parts[2]),
                Float.parseFloat(parts[3]));
    }
}