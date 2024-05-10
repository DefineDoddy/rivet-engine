package me.definedoddy.engine.rendering.texture;

import java.awt.*;

public class Material {
    private final Texture diffuse;
    private final Texture normal;
    private final Texture specular;

    private Color tintColour = Color.WHITE;
    private float reflectivity;

    public Material(Texture diffuse, Texture normal, Texture specular) {
        this.diffuse = diffuse;
        this.normal = normal;
        this.specular = specular;
    }

    public void setTintColour(Color tintColour) {
        this.tintColour = tintColour;
    }

    public Color getTintColour() {
        return tintColour;
    }

    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public Texture getDiffuse() {
        return diffuse;
    }

    public Texture getNormal() {
        return normal;
    }

    public Texture getSpecular() {
        return specular;
    }
}
