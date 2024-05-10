package me.definedoddy.engine.rendering.texture;

public class Material {
    private final Texture diffuse;
    private final Texture normal;
    private final Texture specular;

    private float reflectivity;

    public Material(Texture diffuse, Texture normal, Texture specular) {
        this.diffuse = diffuse;
        this.normal = normal;
        this.specular = specular;
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
