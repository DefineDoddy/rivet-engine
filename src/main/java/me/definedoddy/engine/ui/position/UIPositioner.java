package me.definedoddy.engine.ui.position;

import me.definedoddy.engine.ui.constraint.ConstraintUtils;
import me.definedoddy.engine.ui.constraint.UIConstraints;

public class UIPositioner {
    private UIConstraints constraints = ConstraintUtils.fill();

    public void setConstraints(UIConstraints constraints) {
        this.constraints = constraints;
    }

    public UIConstraints getConstraints() {
        return constraints;
    }

    public float getPixelWidth() {
        return 1f;
    }

    public float getPixelHeight() {
        return 1f;
    }
}
