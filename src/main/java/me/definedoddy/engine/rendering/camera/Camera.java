package me.definedoddy.engine.rendering.camera;

import me.definedoddy.engine.debug.Debug;
import me.definedoddy.engine.entity.Entity;
import me.definedoddy.engine.utils.maths.MathsUtils;
import me.definedoddy.engine.window.GameWindow;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera extends Entity {
    private static Camera activeCamera;

    protected float fieldOfView = 70f;
    protected float nearPlane = 0.1f;
    protected float farPlane = 1000f;

    protected Matrix4f projectionMatrix;
    protected Matrix4f viewMatrix;

    public Camera(Vector3f position) {
        super(position);
    }

    public static <T extends Camera> T getActive(Class<T> cameraClass) {
        return cameraClass.cast(activeCamera);
    }

    public static Camera getActive() {
        return activeCamera;
    }

    @Override
    public void init() {
        super.init();
        calcMatrices();

        if (activeCamera != null) {
            Debug.logWarning("Camera instance already exists. Overriding the previous instance.");
        }

        activeCamera = this;
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

    public void rotate(float pitch, float yaw, float roll) {
        setPitch(this.rotation.x + pitch);
        setYaw(this.rotation.y + yaw);
        setRoll(this.rotation.z + roll);
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
        setRotation(new Vector3f(pitch % 360, rotation.y, rotation.z));
    }

    public float getYaw() {
        return rotation.y;
    }

    public void setYaw(float yaw) {
        setRotation(new Vector3f(rotation.x, yaw % 360, rotation.z));
    }

    public float getRoll() {
        return rotation.z;
    }

    public void setRoll(float roll) {
        setRotation(new Vector3f(rotation.x, rotation.y, roll % 360));
    }
}
