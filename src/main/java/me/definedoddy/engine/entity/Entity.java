package me.definedoddy.engine.entity;

import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Entity {
    protected final UUID uuid = UUID.randomUUID();
    protected final List<Component> components = new ArrayList<>();

    protected final Vector3f position;
    protected final Vector3f rotation;
    protected final Vector3f scale;

    private boolean initialised;
    private boolean removed;

    public Entity() {
        this(new Vector3f());
    }

    public Entity(Vector3f position) {
        this.position = position;
        this.rotation = new Vector3f();
        this.scale = new Vector3f(1, 1, 1);
    }

    public void init() {
        components.forEach(component -> component.init(this));
        initialised = true;
    }

    public void update() {
        components.forEach(Component::update);
    }

    public void remove() {
        components.forEach(Component::remove);
        removed = true;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void addComponent(Component component) {
        components.add(component);
        if (initialised) component.init(this);
    }

    public void removeComponent(Component component) {
        component.remove();
        components.remove(component);
    }

    public <T extends Component> T getComponent(Class<T> type) {
        for (Component component : components) {
            if (type.isAssignableFrom(component.getClass())) {
                return type.cast(component);
            }
        }
        return null;
    }

    public <T extends Component> List<T> getComponents(Class<T> type) {
        List<T> result = new ArrayList<>();
        for (Component component : components) {
            if (type.isAssignableFrom(component.getClass())) {
                result.add(type.cast(component));
            }
        }
        return result;
    }
}
