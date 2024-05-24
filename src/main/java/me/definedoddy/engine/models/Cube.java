package me.definedoddy.engine.models;

import me.definedoddy.engine.rendering.object.mesh.Mesh;
import me.definedoddy.engine.rendering.object.model.Model;
import me.definedoddy.engine.rendering.texture.Material;

public class Cube extends Model {
    //private final Vector3f size;

    public Cube(Mesh mesh, Material material) {
        super(mesh, material);
    }

    /*public Cube(Vector3f size, Material material) {
        super(createMesh(size), material);
        this.size = size;
    }

    private Mesh createMesh() {
        return null;
    }

    public Vector3f getSize() {
        return size;
    }*/
}
