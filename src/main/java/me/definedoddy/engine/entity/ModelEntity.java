package me.definedoddy.engine.entity;

import me.definedoddy.engine.rendering.object.model.Model;

public abstract class ModelEntity extends Entity {
    private final Model model;

    public ModelEntity() {
        model = defineModel();
    }

    protected abstract Model defineModel();

    public Model getModel() {
        return model;
    }
}
