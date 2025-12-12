package org.rivetengine.rendering.texture;

import org.rivetengine.toolkit.memory.Disposable;

import java.awt.*;
import java.util.Objects;

public class Material implements Disposable {
    private Texture diffuseMap;
    private Texture normalMap;
    private Texture specularMap;

    private Color colour = Color.WHITE;
    private float shininess = 0.5f;
    private boolean transparent;

    public static final Material DEFAULT = new Material();

    public void bind() {
        if (diffuseMap != null) diffuseMap.bind(0);
        if (normalMap != null) normalMap.bind(1);
        if (specularMap != null) specularMap.bind(2);
    }

    public void bind(int index) {
        if (diffuseMap != null) diffuseMap.bind(index * 3);
        if (normalMap != null) normalMap.bind(index * 3 + 1);
        if (specularMap != null) specularMap.bind(index * 3 + 2);
    }

    public void unbind() {
        if (diffuseMap != null) diffuseMap.unbind();
        if (normalMap != null) normalMap.unbind();
        if (specularMap != null) specularMap.unbind();
    }

    public Texture getDiffuseMap() {
        return diffuseMap;
    }

    public void setDiffuseMap(Texture diffuseMap) {
        this.diffuseMap = diffuseMap;
    }

    public Texture getNormalMap() {
        return normalMap;
    }

    public void setNormalMap(Texture normalMap) {
        this.normalMap = normalMap;
    }

    public Texture getSpecularMap() {
        return specularMap;
    }

    public void setSpecularMap(Texture specularMap) {
        this.specularMap = specularMap;
    }

    public void setColour(Color colour) {
        this.colour = colour;
    }

    public Color getColour() {
        return colour;
    }

    public void setShininess(float shininess) {
        this.shininess = shininess;
    }

    public float getShininess() {
        return shininess;
    }

    public void setTransparent(boolean transparent) {
        this.transparent = transparent;
    }

    public boolean isTransparent() {
        return transparent;
    }

    @Override
    public void dispose() {
        unbind();
        if (diffuseMap != null) diffuseMap.dispose();
        if (normalMap != null) normalMap.dispose();
        if (specularMap != null) specularMap.dispose();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Material material) {
            return Objects.equals(material.getDiffuseMap(), diffuseMap) &&
                    Objects.equals(material.getNormalMap(), normalMap) &&
                    Objects.equals(material.getSpecularMap(), specularMap) &&
                    Objects.equals(material.getColour(), colour) &&
                    material.getShininess() == shininess;
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
                '}';
    }
}
