package me.definedoddy.engine.entity;

import me.definedoddy.engine.rendering.object.model.Model;
import me.definedoddy.engine.rendering.object.model.ModelUtils;
import me.definedoddy.engine.rendering.texture.Material;
import org.joml.Vector3f;

public class EntityFactory {
    public static ModelEntity createQuad(Vector3f size, Material material) {
        return new ModelEntity() {
            @Override
            protected Model defineModel() {
                return ModelUtils.createQuad(size, material);
            }
        };
    }
}
