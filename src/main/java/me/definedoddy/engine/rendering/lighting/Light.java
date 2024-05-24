package me.definedoddy.engine.rendering.lighting;

import java.awt.*;

public abstract class Light {
    protected Color diffuseColour = Color.WHITE;
    protected Color ambientColour = Color.BLACK;
    protected Color specularColour = Color.WHITE;

    public Color getDiffuseColour() {
        return diffuseColour;
    }

    public void setDiffuseColour(Color diffuseColour) {
        this.diffuseColour = diffuseColour;
    }

    public Color getAmbientColour() {
        return ambientColour;
    }

    public void setAmbientColour(Color ambientColour) {
        this.ambientColour = ambientColour;
    }

    public Color getSpecularColour() {
        return specularColour;
    }

    public void setSpecularColour(Color specularColour) {
        this.specularColour = specularColour;
    }
}
