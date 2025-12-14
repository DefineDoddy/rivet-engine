package org.rivetengine.system;

import java.util.ArrayList;
import java.util.List;

import org.rivetengine.core.Game;
import org.rivetengine.core.Scene;
import org.rivetengine.entity.Entity;
import org.rivetengine.entity.component.Component;

public abstract class GameSystem {
    public abstract void update(Game game, float dt);

    public void onSceneLoad(Scene scene) {
    }

    public void onSceneUnload(Scene scene) {
    }

    protected <T extends Component> List<Entity> getEntitiesWith(Game game, Class<T> componentClass) {
        List<Entity> entitiesWithComponent = new ArrayList<>();

        for (Entity entity : game.getActiveScene().getAllEntities()) {
            if (entity.hasComponent(componentClass)) {
                entitiesWithComponent.add(entity);
            }
        }

        return entitiesWithComponent;
    }
}
