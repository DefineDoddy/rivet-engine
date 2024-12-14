package me.definedoddy.engine.rendering.camera;

import me.definedoddy.engine.entity.Entity;
import me.definedoddy.engine.utils.maths.MathsUtils;
import me.definedoddy.engine.window.GameWindow;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera extends Entity {
    private static Camera instance;

    protected float fieldOfView = 70f;
    protected float nearPlane = 0.1f;
    protected float farPlane = 1000f;

    protected Matrix4f projectionMatrix;
    protected Matrix4f viewMatrix;

    public static Camera get() {
        return instance;
    }

    public static void set(Camera camera) {
        instance = camera;
    }

    @Override
    public void init() {
        Camera.set(this);

        super.init();
        calcMatrices();
    }

    @Override
    public void update() {
        super.update();
        calcViewMatrix();
    }

    private void calcMatrices() {
        calcProjectionMatrix();
        calcViewMatrix();
    }

    private void calcProjectionMatrix() {
        float aspectRatio = (float) GameWindow.get().getWidth() / GameWindow.get().getHeight();
        projectionMatrix = new Matrix4f().perspective((float) Math.toRadians(fieldOfView), aspectRatio, nearPlane, farPlane);
    }

    private void calcViewMatrix() {
        viewMatrix = MathsUtils.createViewMatrix(this);
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    public void move(Vector3f vector) {
        position.add(vector);
        calcViewMatrix();
    }

    public void rotate(float pitch, float yaw, float roll) {
        setPitch(this.rotation.x + pitch);
        setYaw(this.rotation.y + yaw);
        setRoll(this.rotation.z + roll);
    }

    public void setPosition(Vector3f position) {
        this.position.set(position);
    }

    public float getFieldOfView() {
        return fieldOfView;
    }

    public void setFieldOfView(float fieldOfView) {
        this.fieldOfView = fieldOfView;
        calcProjectionMatrix();
    }

    public float getNearPlane() {
        return nearPlane;
    }

    public void setNearPlane(float nearPlane) {
        this.nearPlane = nearPlane;
        calcProjectionMatrix();
    }

    public float getFarPlane() {
        return farPlane;
    }

    public void setFarPlane(float farPlane) {
        this.farPlane = farPlane;
        calcProjectionMatrix();
    }

    public float getPitch() {
        return rotation.x;
    }

    public void setPitch(float pitch) {
        this.rotation.x = pitch % 360;
    }

    public float getYaw() {
        return rotation.y;
    }

    public void setYaw(float yaw) {
        this.rotation.y = yaw % 360;
    }

    public float getRoll() {
        return rotation.z;
    }

    public void setRoll(float roll) {
        this.rotation.z = roll % 360;
    }
}
