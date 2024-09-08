package me.definedoddy.engine.ui;

import me.definedoddy.engine.ui.position.UIPositioner;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public abstract class UIComponent {
    private final UIPositioner positioner = new UIPositioner();

    private final Vector2f position = new Vector2f(0.73f, 0.7f);
    private final Vector2f size = new Vector2f(0.25f, 0.25f);

    private final List<UIComponent> children = new ArrayList<>();

    public void update() {
        for (UIComponent child : getChildren()) {
            child.update();
        }
    }

    public UIPositioner getPositioner() {
        return positioner;
    }

    public Vector2f getPosition() {
        return position;
    }

    public Vector2f getSize() {
        return size;
    }

    public void addChild(UIComponent child) {
        children.add(child);
    }

    public void removeChild(UIComponent child) {
        children.remove(child);
    }

    public List<UIComponent> getChildren() {
        return children;
    }
}
