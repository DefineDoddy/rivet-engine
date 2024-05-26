package me.definedoddy.engine.rendering.texture;

public class MaterialBuilder {
    private Texture diffuse;
    private Texture normal;
    private Texture specular;

    private float shininess = 0.5f;

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

    public MaterialBuilder shininess(float shininess) {
        this.shininess = shininess;
        return this;
    }

    public MaterialBuilder transparent() {
        transparent = true;
        return this;
    }

    public Material build() {
        Material material = new Material(diffuse, normal, specular);
        material.setShininess(shininess);
        material.setTransparent(transparent);
        return material;
    }
}
