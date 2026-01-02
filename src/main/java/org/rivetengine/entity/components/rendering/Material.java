package org.rivetengine.entity.components.rendering;

import java.awt.Color;

import org.rivetengine.entity.component.Component;
import org.rivetengine.rendering.texture.Texture;
import org.rivetengine.toolkit.memory.Handle;

public class Material implements Component {
    public Color diffuse;
    public Color specular;
    public float shininess;

    public Handle<Texture> diffuseMap;
    public Handle<Texture> normalMap;
    public Handle<Texture> specularMap;

    public Material() {
        this.diffuse = Color.WHITE;
        this.specular = Color.WHITE;
        this.shininess = 0.5f;
    }

    public Material(Color diffuse) {
        this();
        this.diffuse = diffuse;
    }

    public Material(Handle<Texture> diffuseMap) {
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
