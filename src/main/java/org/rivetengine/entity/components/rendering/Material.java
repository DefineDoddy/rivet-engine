package org.rivetengine.entity.components.rendering;

import java.awt.Color;

import org.rivetengine.entity.component.Component;

public class Material implements Component {
    public Color diffuse;
    public Color specular;
    public float shininess;
    public String diffuseMap;
    public String normalMap;
    public String specularMap;

    public Material() {
        this.diffuse = Color.WHITE;
        this.specular = Color.WHITE;
        this.shininess = 32f;
        this.diffuseMap = null;
        this.normalMap = null;
        this.specularMap = null;
    }

    public Material(Color diffuse) {
        this();
        this.diffuse = diffuse;
    }

    public Material(String diffuseMap) {
        this();
        this.diffuseMap = diffuseMap;
    }

    public boolean hasTexture() {
        return diffuseMap != null;
    }

    public boolean hasNormalMap() {
        return normalMap != null;
    }

    public boolean hasSpecularMap() {
        return specularMap != null;
    }
}
