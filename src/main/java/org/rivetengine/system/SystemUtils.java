package org.rivetengine.system;

import java.util.ArrayList;
import java.util.List;

import org.rivetengine.core.Game;
import org.rivetengine.entity.Entity;
import org.rivetengine.entity.component.Component;
import org.rivetengine.entity.components.Transform;

public class SystemUtils {
    public static <T extends Component> List<Entity> getEntitiesWithComponent(Game game, Class<T> componentClass) {
        List<Entity> entitiesWithComponent = new ArrayList<>();

        for (Entity entity : game.getActiveScene().getAllEntities()) {
            if (entity.hasComponent(componentClass)) {
                entitiesWithComponent.add(entity);
            }
        }

        return entitiesWithComponent;
    }

    public static Transform getTransformSafe(Entity entity) {
        Transform transform = entity.getComponent(Transform.class);

        if (transform == null) {
            transform = new Transform();
        }

        return transform;
    }
}
