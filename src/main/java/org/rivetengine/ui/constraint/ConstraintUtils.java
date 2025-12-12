package org.rivetengine.ui.constraint;

public class ConstraintUtils {
    public static UIConstraints getRelative(float x, float y, float width, float height) {
        return new UIConstraints(
                new RelativeConstraint(x),
                new RelativeConstraint(y),
                new RelativeConstraint(width),
                new RelativeConstraint(height)
        );
    }

    public static UIConstraints getRelativePos(float x, float y) {
        UIConstraints cons = new UIConstraints();
        cons.setX(new RelativeConstraint(x));
        cons.setY(new RelativeConstraint(y));
        return cons;
    }

    public static UIConstraints fill() {
        return getRelative(0, 0, 1, 1);
    }
}
