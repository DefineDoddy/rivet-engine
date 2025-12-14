package org.rivetengine.rendering.material;

import java.awt.*;

import org.rivetengine.rendering.texture.Texture;

public class MaterialBuilder {
    private final Material ref = new Material();

    private Texture diffuse;
    private Texture normal;
    private Texture specular;

    private Color colour = Color.WHITE;
    private float shininess = ref.shininess;

    private boolean transparent;

    public static Material fromDiffuse(Texture diffuse) {
        return new MaterialBuilder().diffuse(diffuse).build();
    }

    public MaterialBuilder diffuse(Texture diffuse) {
        this.diffuse = diffuse;
        return this;
    }

    public MaterialBuilder normal(Texture normal) {
        this.normal = normal;
        return this;
    }

    public MaterialBuilder specular(Texture specular) {
        this.specular = specular;
        return this;
    }

    public MaterialBuilder colour(Color colour) {
        this.colour = colour;
        return this;
    }

    public MaterialBuilder shininess(float shininess) {
        this.shininess = shininess;
        return this;
    }

    public MaterialBuilder transparent() {
        transparent = true;
        return this;
    }

    public Material build() {
        Material material = new Material();
        material.diffuseMap = diffuse;
        material.normalMap = normal;
        material.specularMap = specular;
        material.colour = colour;
        material.shininess = shininess;
        material.transparent = transparent;
        return material;
    }
}
