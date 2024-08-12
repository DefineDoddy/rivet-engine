package me.definedoddy.engine.rendering.texture;

import java.awt.*;

public class MaterialBuilder {
    private final Material ref = new Material();

    private Texture diffuse;
    private Texture normal;
    private Texture specular;

    private Color colour = Color.WHITE;
    private float shininess = ref.getShininess();

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
        material.setDiffuseMap(diffuse);
        material.setNormalMap(normal);
        material.setSpecularMap(specular);
        material.setColour(colour);
        material.setShininess(shininess);
        material.setTransparent(transparent);
        return material;
    }
}
