package org.rivetengine.ui.constraint;

import org.rivetengine.ui.UIComponent;
import org.rivetengine.toolkit.maths.Axis2d;
import org.rivetengine.toolkit.types.EnumFlag;

public abstract class UIConstraint {
    private final EnumFlag<Type> supported = new EnumFlag<>(Type.POSITION, Type.SCALE);

    private Type type;
    private Axis2d axis;

    private UIComponent current;
    private UIComponent parent;

    public abstract void init();
    public abstract float getRelativeValue();
    public abstract void setRelativeValue(float value);
    public abstract void setPixelValue(int pixels);

    public void setAxisType(Type type, Axis2d axis) {
        this.type = type;
        this.axis = axis;

        if (!supported.has(type)) {
            throw new IllegalArgumentException("This constraint does not support " + type.name().toLowerCase());
        }
    }

    public Axis2d getAxis() {
        return axis;
    }

    public Type getType() {
        return type;
    }

    public UIComponent getParent() {
        return parent;
    }

    public UIComponent getComponent() {
        return current;
    }

    public float pixelsToRelative(int pixels) {
        return pixels / getParentPixelSize();
    }

    public float getParentPixelSize() {
        return axis == Axis2d.X ? parent.getPositioner().getPixelWidth() : parent.getPositioner().getPixelHeight();
    }

    public void refresh() {

    }

    public enum Type {
        POSITION,
        SCALE
    }
}
