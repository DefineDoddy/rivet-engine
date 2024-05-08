package me.definedoddy.engine.entity;

import me.definedoddy.engine.rendering.object.model.Model;
import me.definedoddy.engine.rendering.object.model.ModelUtils;
import me.definedoddy.engine.rendering.texture.Texture;
import org.joml.Vector3f;

public class EntityFactory {
    public static ModelEntity createQuad(Vector3f size, Texture texture) {
        return new ModelEntity() {
            @Override
            protected Model defineModel() {
                return ModelUtils.createQuad(size, texture);
            }
        };
    }
}
