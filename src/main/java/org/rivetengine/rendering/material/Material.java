package org.rivetengine.rendering.material;

import org.rivetengine.rendering.texture.Texture;
import org.rivetengine.toolkit.memory.Disposable;

import java.awt.*;
import java.util.Objects;

public class Material implements Disposable {
    public Texture diffuseMap;
    public Texture normalMap;
    public Texture specularMap;

    public Color colour = Color.WHITE;
    public float shininess = 0.5f;
    public boolean transparent;

    public void bind() {
        if (diffuseMap != null) {
            diffuseMap.bind(0);
        }
        if (normalMap != null) {
            normalMap.bind(1);
        }
        if (specularMap != null) {
            specularMap.bind(2);
        }
    }

    public void bind(int index) {
        if (diffuseMap != null) {
            diffuseMap.bind(index * 3);
        }
        if (normalMap != null) {
            normalMap.bind(index * 3 + 1);
        }
        if (specularMap != null) {
            specularMap.bind(index * 3 + 2);
        }
    }

    public void unbind() {
        if (diffuseMap != null) {
            diffuseMap.unbind();
        }
        if (normalMap != null) {
            normalMap.unbind();
        }
        if (specularMap != null) {
            specularMap.unbind();
        }
    }

    @Override
    public void dispose() {
        unbind();

        if (diffuseMap != null) {
            diffuseMap.dispose();
        }
        if (normalMap != null) {
            normalMap.dispose();
        }
        if (specularMap != null) {
            specularMap.dispose();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Material material) {
            return Objects.equals(material.diffuseMap, diffuseMap) &&
                    Objects.equals(material.normalMap, normalMap) &&
                    Objects.equals(material.specularMap, specularMap) &&
                    Objects.equals(material.colour, colour) &&
                    material.shininess == shininess;
        }
        return false;
    }

    @Override
    public Material clone() {
        Material material = new Material();
        material.diffuseMap = diffuseMap;
        material.normalMap = normalMap;
        material.specularMap = specularMap;
        material.colour = colour;
        material.shininess = shininess;
        material.transparent = transparent;
        return material;
    }

    @Override
    public String toString() {
        return "Material{" +
                "diffuseMap=" + diffuseMap +
                ", normalMap=" + normalMap +
                ", specularMap=" + specularMap +
                ", colour=" + colour +
                ", shininess=" + shininess +
                ", transparent=" + transparent +
                "}";
    }
}
