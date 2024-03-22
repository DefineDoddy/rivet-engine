package me.definedoddy.engine.entity;

import java.util.UUID;

public class Entity {
    private final UUID uuid = UUID.randomUUID();

    public UUID getUuid() {
        return uuid;
    }
}
