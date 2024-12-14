package me.definedoddy.engine.entity;

import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Entity {
    protected final UUID uuid = UUID.randomUUID();

    protected Entity parent = null;

    protected final List<Entity> children = new ArrayList<>();
    protected final List<Component> components = new ArrayList<>();

    protected final Vector3f position;
    protected final Vector3f rotation;
    protected final Vector3f scale;

    protected final Vector3f localPosition;
    protected final Vector3f localRotation;
    protected final Vector3f localScale;

    private boolean initialised;
    private boolean removed;

    public Entity() {
        this(new Vector3f());
    }

    public Entity(Vector3f position) {
        this.position = position;
        this.rotation = new Vector3f();
        this.scale = new Vector3f(1, 1, 1);

        this.localPosition = new Vector3f();
        this.localRotation = new Vector3f();
        this.localScale = new Vector3f(1, 1, 1);
    }

    public void init() {
        components.forEach(component -> component.init(this));

        children.forEach(entity -> {
            entity.init();
            entity.parent = this;
        });

        initialised = true;
    }

    public void update() {
        components.forEach(Component::update);

        children.forEach(entity -> {
            entity.position.set(entity.localPosition.add(position, new Vector3f()));
            entity.rotation.set(entity.localRotation.add(rotation, new Vector3f()));
            entity.scale.set(entity.localScale.mul(scale, new Vector3f()));
            entity.update();
        });
    }

    public void remove() {
        components.forEach(Component::remove);
        children.forEach(Entity::remove);
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

    public <T extends Component> T addComponent(T component) {
        components.add(component);
        if (initialised) component.init(this);
        return component;
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

    public <T extends Entity> T addChild(T entity) {
        children.add(entity);
        if (initialised) {
            entity.init();
            entity.parent = this;
        }
        return entity;
    }

    public void removeChild(Entity entity) {
        entity.remove();
        children.remove(entity);
    }

    public List<Entity> getChildren() {
        return children;
    }

    public <T extends Entity> T getChild(Class<T> type) {
        for (Entity entity : children) {
            if (type.isAssignableFrom(entity.getClass())) {
                return type.cast(entity);
            }
        }
        return null;
    }

    public <T extends Entity> List<T> getChildren(Class<T> type) {
        List<T> result = new ArrayList<>();
        for (Entity entity : children) {
            if (type.isAssignableFrom(entity.getClass())) {
                result.add(type.cast(entity));
            }
        }
        return result;
    }
}
