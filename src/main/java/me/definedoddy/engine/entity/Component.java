package me.definedoddy.engine.entity;

public abstract class Component {
    private Entity entity;

    void init(Entity entity) {
        this.entity = entity;
    }

    public abstract void init();
    public abstract void update();
    public abstract void remove();

    public Entity getEntity() {
        return entity;
    }
}
