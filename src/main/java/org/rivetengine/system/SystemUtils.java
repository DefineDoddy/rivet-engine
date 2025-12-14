package org.rivetengine.system;

import org.rivetengine.entity.Entity;
import org.rivetengine.entity.components.Transform;

public class SystemUtils {
    public static Transform getTransformSafe(Entity entity) {
        Transform transform = entity.getComponent(Transform.class);

        if (transform != null) {
            transform = new Transform();
        }

        return transform;
    }
}
