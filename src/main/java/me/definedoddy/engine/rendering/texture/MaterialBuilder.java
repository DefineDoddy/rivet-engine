package me.definedoddy.engine.rendering.texture;

public class MaterialBuilder {
    private final Texture diffuse;

    private Texture normal;
    private Texture specular;

    private float reflectivity = 0.5f;

    public MaterialBuilder(Texture diffuse) {
        this.diffuse = diffuse;
    }

    public MaterialBuilder normal(Texture normal) {
        this.normal = normal;
        return this;
    }

    public MaterialBuilder specular(Texture specular) {
        this.specular = specular;
        return this;
    }

    public MaterialBuilder reflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
        return this;
    }

    public Material build() {
        Material material = new Material(diffuse, normal, specular);
        material.setReflectivity(reflectivity);
        return material;
    }
}
