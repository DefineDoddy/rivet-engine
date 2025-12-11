package me.definedoddy.engine.entity;

public interface Component {
    default void onAttach(Entity entity) {
    }

    default void onDetach(Entity entity) {
    }
}
