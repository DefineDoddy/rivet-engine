package me.definedoddy.engine.ui.constraint;

public class RelativeConstraint extends UIConstraint {
    private float relativeValue;

    public RelativeConstraint(float value) {
        this.relativeValue = value;
    }

    @Override
    public void init() {

    }

    @Override
    public float getRelativeValue() {
        return relativeValue;
    }

    @Override
    public void setRelativeValue(float value) {
        if (relativeValue == value) return;
        relativeValue = value;
        refresh();
    }

    @Override
    public void setPixelValue(int pixels) {
        relativeValue = pixels / getParentPixelSize();
    }
}
