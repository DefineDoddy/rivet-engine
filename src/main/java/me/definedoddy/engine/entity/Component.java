package me.definedoddy.engine.entity;

public abstract class Component {
    private Entity entity;
    private boolean enabled = true;

    void init(Entity entity) {
        this.entity = entity;
        init();
    }

    public abstract void init();
    public abstract void update();
    public abstract void remove();

    public Entity getEntity() {
        return entity;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
