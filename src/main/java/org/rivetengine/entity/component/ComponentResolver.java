package org.rivetengine.entity.component;

import org.rivetengine.debug.Debug;
import org.rivetengine.entity.Entity;

public class ComponentResolver {
    public static <T extends Component> void checkDependencies(Entity entity, T component) {
        Requires requires = component.getClass().getAnnotation(Requires.class);
        if (requires != null) {
            for (Class<? extends Component> requiredClass : requires.value()) {
                if (!entity.hasComponent(requiredClass)) {
                    throw new IllegalStateException("Component " + component.getClass().getSimpleName() +
                            " requires component " + requiredClass.getSimpleName() + " to be present on Entity "
                            + entity.id);
                }
            }
        }
    }

    public static <T extends Component> void resolveDependencies(Entity entity, T component) {
        Requires requires = component.getClass().getAnnotation(Requires.class);
        if (requires != null) {
            for (Class<? extends Component> requiredClass : requires.value()) {
                if (!entity.hasComponent(requiredClass)) {
                    try {
                        Component requiredComponent = requiredClass.getDeclaredConstructor().newInstance();
                        entity.addComponent(requiredComponent, true);
                    } catch (Exception e) {
                        throw new RuntimeException(
                                "Failed to instantiate required component: " + requiredClass.getSimpleName(), e);
                    }
                }
            }
        }

        OptionallyRequires optionallyRequires = component.getClass().getAnnotation(OptionallyRequires.class);
        if (optionallyRequires != null) {
            for (Class<? extends Component> optionalClass : optionallyRequires.value()) {
                if (!entity.hasComponent(optionalClass)) {
                    try {
                        Component optionalComponent = optionalClass.getDeclaredConstructor().newInstance();
                        entity.addComponent(optionalComponent, true);
                    } catch (Exception e) {
                        Debug.logWarn("Failed to instantiate optional component: " + optionalClass.getSimpleName());
                    }
                }
            }
        }
    }
}
