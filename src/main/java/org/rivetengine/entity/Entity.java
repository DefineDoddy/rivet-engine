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
    private final UUID id;

    private final Map<Class<? extends Component>, Component> components = new HashMap<>();
    private final List<Entity> children = new ArrayList<>();
    private Entity parent = null;

    public Entity() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public <T extends Component> void addComponent(T component) {
        addComponent(component, false);
    }

    public <T extends Component> void addComponent(T component, boolean resolveDependencies) {
        components.put(component.getClass(), component);
        component.onAttach(this);

        if (resolveDependencies) {
            ComponentResolver.resolveDependencies(this, component);
        } else {
            ComponentResolver.checkDependencies(this, component);
        }
    }

    public <T extends Component> void removeComponent(Class<T> componentClass) {
        Component component = components.remove(componentClass);

        if (component != null) {
            component.onDetach(this);
        }
    }

    public <T extends Component> T getComponent(Class<T> componentClass) {
        return componentClass.cast(components.get(componentClass));
    }

    public boolean hasComponent(Class<? extends Component> componentClass) {
        return components.containsKey(componentClass);
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