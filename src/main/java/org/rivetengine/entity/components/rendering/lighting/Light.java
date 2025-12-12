package org.rivetengine.entity.components.rendering.lighting;

import java.awt.*;

import org.rivetengine.entity.component.Component;

public abstract class Light implements Component {
    public Color colour = Color.WHITE;
    public float intensity = 1f;
}
