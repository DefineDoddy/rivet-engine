package me.definedoddy.engine.entity;

import me.definedoddy.engine.models.Cube;
import me.definedoddy.engine.models.Quad;
import me.definedoddy.engine.rendering.model.model.Model;
import me.definedoddy.engine.rendering.texture.Material;
import org.joml.Vector3f;

public class EntityFactory {
    private EntityFactory() {}

    public static ModelEntity createQuad(Vector3f size) {
        return createQuad(size, new Material());
    }

    public static ModelEntity createQuad(Vector3f size, Material material) {
        return new ModelEntity() {
            @Override
            protected Model defineModel() {
                return new Quad(size, material);
            }
        };
    }

    public static ModelEntity createCube(Vector3f size) {
        return createCube(size, new Material());
    }

    public static ModelEntity createCube(Vector3f size, Material material) {
        return new ModelEntity() {
            @Override
            protected Model defineModel() {
                return new Cube(size, material);
            }
        };
    }
}
