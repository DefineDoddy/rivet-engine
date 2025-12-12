package org.rivetengine.entity.components.rendering.lighting;

import java.awt.*;

import org.rivetengine.entity.component.OptionallyRequires;
import org.rivetengine.entity.components.Transform;

@OptionallyRequires(Transform.class)
public class PointLight extends Light {
    public float radius = 10f;

    public PointLight(Color colour, float intensity) {
        this.colour = colour;
        this.intensity = intensity;
    }
}
