package me.definedoddy.engine.entity.components;

import me.definedoddy.engine.entity.Component;
import me.definedoddy.engine.entity.Entity;
import me.definedoddy.engine.utils.maths.MathsUtils;
import me.definedoddy.engine.window.GameWindow;
import org.joml.Matrix4f;

public class Camera implements Component {
    protected float fieldOfView = 70f;
    protected float nearPlane = 0.1f;
    protected float farPlane = 1000f;
    protected boolean active = true;

    protected Matrix4f projectionMatrix;
    protected Matrix4f viewMatrix;

    @Override
    public void onAttach(Entity entity) {
        updateProjectionMatrix();
        updateViewMatrix();
    }

    public void update() {
        updateViewMatrix();
    }

    private void updateProjectionMatrix() {
        float aspectRatio = (float) GameWindow.get().getWidth() / GameWindow.get().getHeight();
        projectionMatrix = new Matrix4f().perspective((float) Math.toRadians(fieldOfView), aspectRatio, nearPlane,
                farPlane);
    }

    private void updateViewMatrix() {
        viewMatrix = MathsUtils.createViewMatrix(this);
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    public float getFieldOfView() {
        return fieldOfView;
    }

    public void setFieldOfView(float fieldOfView) {
        this.fieldOfView = fieldOfView;
        updateProjectionMatrix();
    }

    public float getNearPlane() {
        return nearPlane;
    }

    public void setNearPlane(float nearPlane) {
        this.nearPlane = nearPlane;
        updateProjectionMatrix();
    }

    public float getFarPlane() {
        return farPlane;
    }

    public void setFarPlane(float farPlane) {
        this.farPlane = farPlane;
        updateProjectionMatrix();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
