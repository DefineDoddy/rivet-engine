package me.definedoddy.engine.rendering.camera;

import me.definedoddy.engine.window.GameWindow;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class RayCastUtils {
    public static Vector3f screenToWorldPoint(float x, float y) {
        Vector2f normalizedCoords = screenToNormalisedDeviceCoords(x, y);
        Vector4f clipCoords = new Vector4f(normalizedCoords.x, normalizedCoords.y, -1f, 1f);
        Vector4f eyeCoords = clipToEyeCoords(clipCoords);
        return eyeToWorldPoint(eyeCoords);
    }

    public static Vector3f eyeToWorldPoint(Vector4f eyeCoords) {
        Matrix4f invertedView = new Matrix4f(Camera.get().getViewMatrix()).invert();
        Vector4f rayWorld = invertedView.transform(eyeCoords, new Vector4f());
        Vector3f rayPoint = new Vector3f(rayWorld.x, rayWorld.y, rayWorld.z);
        rayPoint.normalize();
        return rayPoint;
    }

    public static Vector4f clipToEyeCoords(Vector4f clipCoords) {
        Matrix4f invertedProjection = new Matrix4f(Camera.get().getProjectionMatrix()).invert();
        Vector4f eyeCoords = invertedProjection.transform(clipCoords, new Vector4f());
        return new Vector4f(eyeCoords.x, eyeCoords.y, -1f, 0f);
    }

    public static Vector2f screenToNormalisedDeviceCoords(float screenX, float screenY) {
        float x = (screenX * 2f) / GameWindow.get().getWidth() - 1;
        float y = (screenY * 2f) / GameWindow.get().getHeight() - 1;
        return new Vector2f(x, -y);
    }
}
