package me.definedoddy.engine.rendering.camera;

import me.definedoddy.engine.utils.maths.MathsUtils;
import me.definedoddy.engine.window.GameWindow;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {
    private static Camera instance;

    private Vector3f position = new Vector3f(0, 0, 0);
    private float pitch, yaw, roll;

    private float fieldOfView = 70f;
    private float nearPlane = 0.1f;
    private float farPlane = 1000f;

    private Matrix4f projectionMatrix;
    private Matrix4f viewMatrix;

    public Camera() {
        calcMatrices();
    }

    public static Camera get() {
        return instance;
    }

    public static void set(Camera camera) {
        instance = camera;
    }

    public void init() {

    }

    public void update() {
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
        this.pitch += pitch;
        this.yaw += yaw;
        this.roll += roll;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public float getFieldOfView() {
        return fieldOfView;
    }

    public void setFieldOfView(float fieldOfView) {
        this.fieldOfView = fieldOfView;
        calcMatrices();
    }

    public float getNearPlane() {
        return nearPlane;
    }

    public void setNearPlane(float nearPlane) {
        this.nearPlane = nearPlane;
        calcMatrices();
    }

    public float getFarPlane() {
        return farPlane;
    }

    public void setFarPlane(float farPlane) {
        this.farPlane = farPlane;
        calcMatrices();
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getRoll() {
        return roll;
    }

    public void setRoll(float roll) {
        this.roll = roll;
    }
}
