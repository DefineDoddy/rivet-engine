package me.definedoddy.engine.entity;

import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Entity {
    protected final UUID uuid = UUID.randomUUID();

    protected final List<Entity> children = new ArrayList<>();
    protected final List<Component> components = new ArrayList<>();
    protected final List<String> tags = new ArrayList<>();

    // Primary storage for the local transform
    protected final Vector3f position;
    protected final Vector3f rotation;
    protected final Vector3f scale;

    // These represent the local transform relative to the parent
    // Keep explicit references for clarity (they will alias the primary storage)
    protected final Vector3f localPosition;
    protected final Vector3f localRotation;
    protected final Vector3f localScale;

    private boolean initialised;
    private boolean removed;
    private Entity parent;

    public Entity(Vector3f position) {
        // Initialize local transform (primary storage)
        this.position = position != null ? position : new Vector3f();
        this.rotation = new Vector3f();
        this.scale = new Vector3f(1, 1, 1);

        // Alias local* to the primary storage so older code that used position/rotation/scale keeps working
        this.localPosition = this.position;
        this.localRotation = this.rotation;
        this.localScale = this.scale;
    }

    public final void init(Entity parent) {
        this.parent = parent;
        components.forEach(component -> component.init(this));
        children.forEach(child -> child.init(this));
        initialised = true;
        init();
    }

    public void init() {

    }

    public void update() {
        components.forEach(Component::update);
        children.forEach(Entity::update);
    }

    public void remove() {
        components.forEach(Component::remove);
        children.forEach(Entity::remove);
        removed = true;
    }

    public UUID getUuid() {
        return uuid;
    }

    // World-space getters/setters (backwards-compatible with existing getPosition()/setPosition())
    public Vector3f getWorldPosition() {
        return getPosition();
    }

    public void setWorldPosition(Vector3f position) {
        setPosition(position);
    }

    // Calculate world position by adding parent's world position
    public Vector3f getPosition() {
        Vector3f worldPosition = new Vector3f(localPosition);
        if (parent != null) {
            worldPosition.add(parent.getPosition());
        }
        return worldPosition;
    }

    // Setting the world position requires calculating the new local position
    public void setPosition(Vector3f position) {
        if (position == null) return;
        if (parent != null) {
            Vector3f parentWorldPosition = parent.getPosition();
            this.localPosition.set(position).sub(parentWorldPosition);
        } else {
            this.localPosition.set(position);
        }
    }

    // World-space rotation
    public Vector3f getWorldRotation() {
        return getRotation();
    }

    public void setWorldRotation(Vector3f rotation) {
        setRotation(rotation);
    }

    // Calculate world rotation by adding parent's world rotation
    public Vector3f getRotation() {
        Vector3f worldRotation = new Vector3f(localRotation);
        if (parent != null) {
            worldRotation.add(parent.getRotation());
        }
        return worldRotation;
    }

    // Setting the world rotation requires calculating the new local rotation
    public void setRotation(Vector3f rotation) {
        if (rotation == null) return;
        if (parent != null) {
            Vector3f parentWorldRotation = parent.getRotation();
            this.localRotation.set(rotation).sub(parentWorldRotation);
        } else {
            this.localRotation.set(rotation);
        }
    }

    // World-space scale
    public Vector3f getWorldScale() {
        return getScale();
    }

    public void setWorldScale(Vector3f scale) {
        setScale(scale);
    }

    // Calculate world scale by multiplying with parent's world scale
    public Vector3f getScale() {
        Vector3f worldScale = new Vector3f(localScale);
        if (parent != null) {
            worldScale.mul(parent.getScale());
        }
        return worldScale;
    }

    // Setting the world scale requires calculating the new local scale
    public void setScale(Vector3f scale) {
        if (scale == null) return;
        if (parent != null) {
            Vector3f parentWorldScale = parent.getScale();
            // Avoid division by zero
            float px = parentWorldScale.x == 0 ? 1f : parentWorldScale.x;
            float py = parentWorldScale.y == 0 ? 1f : parentWorldScale.y;
            float pz = parentWorldScale.z == 0 ? 1f : parentWorldScale.z;
            this.localScale.set(scale.x / px, scale.y / py, scale.z / pz);
        } else {
            this.localScale.set(scale);
        }
    }

    // Explicit local transform accessors
    public Vector3f getLocalPosition() {
        return new Vector3f(localPosition);
    }

    public void setLocalPosition(Vector3f localPosition) {
        if (localPosition == null) return;
        this.localPosition.set(localPosition);
    }

    public Vector3f getLocalRotation() {
        return new Vector3f(localRotation);
    }

    public void setLocalRotation(Vector3f localRotation) {
        if (localRotation == null) return;
        this.localRotation.set(localRotation);
    }

    public Vector3f getLocalScale() {
        return new Vector3f(localScale);
    }

    public void setLocalScale(Vector3f localScale) {
        if (localScale == null) return;
        this.localScale.set(localScale);
    }

    /**
     * Reparent this entity to a new parent. If keepWorldTransform is true, the entity's world transform
     * will remain the same after reparenting (local transform will be adjusted). If false, local transform
     * remains unchanged and the world transform may change.
     */
    public void reparent(Entity newParent, boolean keepWorldTransform) {
        // Capture current world transform
        Vector3f worldPos = getPosition();
        Vector3f worldRot = getRotation();
        Vector3f worldScale = getScale();

        // Remove from current parent's children list if present
        if (this.parent != null) {
            this.parent.children.remove(this);
        }

        // Assign new parent
        this.parent = newParent;

        // Add to new parent's children list
        if (newParent != null && !newParent.children.contains(this)) {
            newParent.children.add(this);
        }

        if (keepWorldTransform) {
            // Compute new local transform so world transform stays the same
            if (newParent != null) {
                Vector3f parentWorldPos = newParent.getPosition();
                Vector3f parentWorldRot = newParent.getRotation();
                Vector3f parentWorldScale = newParent.getScale();

                this.localPosition.set(worldPos).sub(parentWorldPos);
                this.localRotation.set(worldRot).sub(parentWorldRot);

                float px = parentWorldScale.x == 0 ? 1f : parentWorldScale.x;
                float py = parentWorldScale.y == 0 ? 1f : parentWorldScale.y;
                float pz = parentWorldScale.z == 0 ? 1f : parentWorldScale.z;
                this.localScale.set(worldScale.x / px, worldScale.y / py, worldScale.z / pz);
            } else {
                // No parent -> local == world
                this.localPosition.set(worldPos);
                this.localRotation.set(worldRot);
                this.localScale.set(worldScale);
            }
        }
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

    public void addTag(String tag) {
        if (!tags.contains(tag)) tags.add(tag);
    }

    public void removeTag(String tag) {
        tags.remove(tag);
    }

    public boolean hasTag(String tag) {
        return tags.contains(tag);
    }

    public List<String> getTags() {
        return tags;
    }

    public List<Entity> getChildren() {
        return children;
    }

    public void addChild(Entity child) {
        if (child == null) return;
        if (!children.contains(child)) {
            // Use reparent to ensure the child's world transform is preserved when attaching
            child.reparent(this, true);
            // If this entity is already initialised, initialize the child with this as parent
            if (initialised) {
                child.init(this);
            }
        }
    }

    public void removeChild(Entity child) {
        if (child == null) return;
        if (children.remove(child)) {
            child.remove();
            child.parent = null;
        }
    }

    public void removeAllChildren() {
        for (Entity child : new ArrayList<>(children)) {
            child.remove();
            child.parent = null;
        }
        children.clear();
    }
}
