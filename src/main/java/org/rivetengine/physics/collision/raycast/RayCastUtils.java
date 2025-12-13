package org.rivetengine.physics.collision.raycast;

import org.rivetengine.entity.components.rendering.Camera;
import org.rivetengine.physics.PhysicsSystem;
import org.rivetengine.entity.components.physics.collision.Collider;
import org.rivetengine.window.GameWindow;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class RayCastUtils {
    public static RayCastHit rayCast(Vector3f origin, Vector3f direction) {
        return new RayCast(origin, direction).cast();
    }

    public static RayCastHit rayCast(Vector3f origin, Vector3f direction, float maxDistance) {
        return new RayCast(origin, direction, maxDistance).cast();
    }

    public static Collider getIntersectingCollider(Vector3f point) {
        for (Collider collider : PhysicsSystem.getColliders()) {
            if (collider.containsPoint(point) && collider.isEnabled()) {
                return collider;
            }
        }

        return null;
    }

    public static Vector3f screenToWorldPoint(float x, float y) {
        Vector2f normalizedCoords = screenToNormalisedDeviceCoords(x, y);
        Vector4f clipCoords = new Vector4f(normalizedCoords.x, normalizedCoords.y, -1f, 1f);
        Vector4f eyeCoords = clipToEyeCoords(clipCoords);
        return eyeToWorldPoint(eyeCoords);
    }

    public static Vector4f clipToEyeCoords(Vector4f clipCoords) {
        Matrix4f invertedProjection = new Matrix4f(Camera.getActive().getProjectionMatrix()).invert();
        Vector4f eyeCoords = invertedProjection.transform(clipCoords, new Vector4f());
        return new Vector4f(eyeCoords.x, eyeCoords.y, -1f, 0f);
    }

    public static Vector3f eyeToWorldPoint(Vector4f eyeCoords) {
        Matrix4f invertedView = new Matrix4f(Camera.getActive().createViewMatrix()).invert();
        Vector4f rayWorld = invertedView.transform(eyeCoords, new Vector4f());
        Vector3f rayPoint = new Vector3f(rayWorld.x, rayWorld.y, rayWorld.z);
        rayPoint.normalize();
        return rayPoint;
    }

    public static Vector2f screenToNormalisedDeviceCoords(float screenX, float screenY) {
        float x = (screenX * 2f) / GameWindow.get().getWidth() - 1;
        float y = (screenY * 2f) / GameWindow.get().getHeight() - 1;
        return new Vector2f(x, -y);
    }
}
