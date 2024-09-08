package me.definedoddy.engine.entity;

import me.definedoddy.engine.rendering.model.model.Model;
import me.definedoddy.toolkit.memory.Disposable;
import org.joml.Vector3f;

public abstract class ModelEntity extends Entity implements Disposable {
    private final Model model;

    public ModelEntity() {
        this(new Vector3f());
    }

    public ModelEntity(Vector3f position) {
        super(position);
        model = defineModel();
    }

    protected abstract Model defineModel();

    public Model getModel() {
        return model;
    }

    @Override
    public void dispose() {
        model.dispose();
    }
}
