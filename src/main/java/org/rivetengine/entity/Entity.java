package org.rivetengine.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.rivetengine.entity.component.Component;
import org.rivetengine.entity.component.ComponentResolver;

public class Entity {
    public final UUID id;

    private final Map<Class<? extends Component>, Component> components = new HashMap<>();
    private final List<Entity> children = new ArrayList<>();
    private Entity parent = null;
    public final String name;

    public Entity() {
        this(null);
    }

    public Entity(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public <T extends Component> void addComponent(T component) {
        addComponent(component, true);
    }

    public <T extends Component> void addComponent(T component, boolean resolveDependencies) {
        if (resolveDependencies) {
            ComponentResolver.resolveDependencies(this, component);
        } else {
            ComponentResolver.checkDependencies(this, component);
        }

        components.put(component.getClass(), component);
        component.onAttach(this);
    }

    public <T extends Component> void removeComponent(Class<T> componentClass) {
        Component component = components.remove(componentClass);

        if (component != null) {
            component.onDetach(this);
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Component> T getComponent(Class<T> componentClass) {
        for (Component component : components.values()) {
            if (componentClass.isInstance(component)) {
                return (T) component;
            }
        }
        return null;
    }

    public boolean hasComponent(Class<? extends Component> componentClass) {
        for (Component component : components.values()) {
            if (componentClass.isInstance(component)) {
                return true;
            }
        }
        return false;
    }

    public List<Component> getComponents() {
        return new ArrayList<>(components.values());
    }

    public void addChild(Entity child) {
        if (child.parent != null) {
            child.parent.removeChild(child);
        }
        child.parent = this;
        children.add(child);
    }

    public void removeChild(Entity child) {
        if (children.remove(child)) {
            child.parent = null;
        }
    }

    public Entity getParent() {
        return parent;
    }

    public List<Entity> getChildren() {
        return children;
    }

    public Entity getChild(String name) {
        return getChild(name, true);
    }

    public Entity getChild(String name, boolean recursive) {
        for (Entity child : children) {
            if (name != null && name.equals(child.name)) {
                return child;
            }

            if (recursive) {
                Entity found = child.getChild(name, true);

                if (found != null) {
                    return found;
                }
            }
        }

        return null;
    }
}