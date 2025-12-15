package org.rivetengine.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.rivetengine.entity.component.Component;
import org.rivetengine.entity.component.ComponentResolver;
import org.rivetengine.entity.components.Name;

public class Entity {
    public final UUID id;

    private final Map<Class<? extends Component>, Component> components = new HashMap<>();
    private final List<Entity> children = new ArrayList<>();
    private Entity parent = null;

    public Entity() {
        this.id = UUID.randomUUID();
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

    public Entity findChild(String name) {
        for (Entity child : children) {
            Name n = child.getComponent(Name.class);

            if (n != null && n.getName().equals(name)) {
                return child;
            }
        }
        return null;
    }

    public Entity findChildRecursive(String name) {
        Entity found = findChild(name);

        if (found != null) {
            return found;
        }

        for (Entity child : children) {
            found = child.findChildRecursive(name);

            if (found != null) {
                return found;
            }
        }

        return null;
    }
}