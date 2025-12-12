package org.rivetengine.ui.constraint;

public class UIConstraints {
    private UIConstraint x, y;
    private UIConstraint width, height;

    public UIConstraints() {}

    public UIConstraints(UIConstraint x, UIConstraint y, UIConstraint width, UIConstraint height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public UIConstraint getX() {
        return x;
    }

    public UIConstraint getY() {
        return y;
    }

    public void setX(UIConstraint constraint) {
        x = constraint;
    }

    public void setY(UIConstraint constraint) {
        y = constraint;
    }

    public UIConstraint getWidth() {
        return width;
    }

    public UIConstraint getHeight() {
        return height;
    }

    public void setWidth(UIConstraint constraint) {
        width = constraint;
    }

    public void setHeight(UIConstraint constraint) {
        height = constraint;
    }
}
