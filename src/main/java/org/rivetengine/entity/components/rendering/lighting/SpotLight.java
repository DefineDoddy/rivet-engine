package org.rivetengine.entity.components.rendering.lighting;

import java.awt.*;

import org.rivetengine.entity.component.OptionallyRequires;
import org.rivetengine.entity.components.Transform;

@OptionallyRequires(Transform.class)
public class SpotLight extends Light {
    public float outerRadius = 10f;
    public float innerRadius;

    public SpotLight(Color colour, float intensity) {
        this.colour = colour;
        this.intensity = intensity;
    }
}
