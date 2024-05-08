package me.definedoddy.engine.entity;

import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Entity {
    private final UUID uuid = UUID.randomUUID();
    private final List<Component> components = new ArrayList<>();

    private final Vector3f position;
    private final Vector3f rotation;
    private final Vector3f scale;

    private boolean removed;

    public Entity() {
        this(new Vector3f());
    }

    public Entity(Vector3f position) {
        this.position = position;
        this.rotation = new Vector3f();
        this.scale = new Vector3f(1, 1, 1);
    }

    public void update() {}

    public void remove() {
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
    }

    public void removeComponent(Component component) {
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
}
