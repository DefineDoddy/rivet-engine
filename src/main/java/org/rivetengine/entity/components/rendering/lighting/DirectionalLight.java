package org.rivetengine.entity.components.rendering.lighting;

import java.awt.Color;

import org.rivetengine.entity.component.OptionallyRequires;
import org.rivetengine.entity.components.Transform;

@OptionallyRequires(Transform.class)
public class DirectionalLight extends Light {
    public DirectionalLight(Color colour, float intensity) {
        this.colour = colour;
        this.intensity = intensity;
    }
}
