package org.rivetengine.entity.components.camera;

import java.util.ArrayList;
import java.util.List;

import org.rivetengine.entity.component.Component;
import org.rivetengine.rendering.postprocessing.PostProcessEffect;

public class PostProcessing implements Component {
    public List<PostProcessEffect> effects = new ArrayList<>();

    public void addEffect(PostProcessEffect effect) {
        effects.add(effect);
    }

    public void removeEffect(Class<? extends PostProcessEffect> type) {
        effects.removeIf(e -> e.getClass() == type);
    }

    public List<PostProcessEffect> getEffects() {
        return effects;
    }
}
