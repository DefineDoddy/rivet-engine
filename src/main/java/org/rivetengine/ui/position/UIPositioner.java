package org.rivetengine.ui.position;

import org.rivetengine.ui.constraint.ConstraintUtils;
import org.rivetengine.ui.constraint.UIConstraints;

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
