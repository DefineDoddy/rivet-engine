package me.definedoddy.engine.rendering.texture;

import me.definedoddy.toolkit.memory.Disposable;

import java.awt.*;

public class Material implements Disposable {
    private final Texture diffuse;
    private final Texture normal;
    private final Texture specular;

    private Color tintColour = Color.WHITE;
    private float shininess;

    private boolean isTransparent;

    public Material(Texture diffuse, Texture normal, Texture specular) {
        this.diffuse = diffuse;
        this.normal = normal;
        this.specular = specular;
    }

    public static Material defaultMaterial() {
        return new Material(null, null, null);
    }

    public void bind() {
        if (diffuse != null) diffuse.bind(0);
        if (normal != null) normal.bind(1);
        if (specular != null) specular.bind(2);
    }

    public void unbind() {
        if (diffuse != null) diffuse.unbind();
        if (normal != null) normal.unbind();
        if (specular != null) specular.unbind();
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

    public void setTintColour(Color tintColour) {
        this.tintColour = tintColour;
    }

    public Color getTintColour() {
        return tintColour;
    }

    public void setShininess(float shininess) {
        this.shininess = shininess;
    }

    public float getShininess() {
        return shininess;
    }

    public void setTransparent(boolean transparent) {
        isTransparent = transparent;
    }

    public boolean isTransparent() {
        return isTransparent;
    }

    @Override
    public void dispose() {
        if (diffuse != null) diffuse.dispose();
        if (normal != null) normal.dispose();
        if (specular != null) specular.dispose();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Material material) {
            return material.getDiffuse().equals(diffuse) &&
                    material.getNormal().equals(normal) &&
                    material.getSpecular().equals(specular) &&
                    material.getTintColour().equals(tintColour) &&
                    material.getShininess() == shininess;
        }
        return false;
    }

    @Override
    public Material clone() {
        return new Material(diffuse, normal, specular);
    }
}
