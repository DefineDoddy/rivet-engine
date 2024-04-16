package me.definedoddy.engine.entity;

import org.joml.Vector3f;

import java.util.UUID;

public class Entity {
    private final UUID uuid = UUID.randomUUID();

    private final Vector3f position;
    private final Vector3f rotation;
    private final Vector3f scale;

    public Entity() {
        this(new Vector3f());
    }

    public Entity(Vector3f position) {
        this.position = position;
        this.rotation = new Vector3f();
        this.scale = new Vector3f(1, 1, 1);
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
}
