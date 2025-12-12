package org.rivetengine.entity.component;

import org.rivetengine.entity.Entity;

public interface Component {
    default void onAttach(Entity entity) {
    }

    default void onDetach(Entity entity) {
    }
}
