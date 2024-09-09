package me.definedoddy.engine.entity;

import me.definedoddy.engine.physics.collision.BoundingBox;
import me.definedoddy.engine.rendering.model.model.Model;
import me.definedoddy.toolkit.memory.Disposable;
import org.joml.Vector3f;

public abstract class ModelEntity extends Entity implements Disposable {
    private final Model model;

    private BoundingBox boundingBox;

    public ModelEntity() {
        this(new Vector3f());
    }

    public ModelEntity(Vector3f position) {
        super(position);
        model = defineModel();
        calculateBoundingBox();
    }

    @Override
    public void update() {
        super.update();
        calculateBoundingBox();
    }

    private void calculateBoundingBox() {
        if (boundingBox == null) boundingBox = getModel().getBoundingBox().clone();
        else boundingBox.set(getModel().getBoundingBox());

        boundingBox.max(new Vector3f(0.5f));
        boundingBox.scale(getScale());
        boundingBox.translate(getPosition());
    }

    protected abstract Model defineModel();

    public Model getModel() {
        return model;
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    @Override
    public void dispose() {
        model.dispose();
    }
}
