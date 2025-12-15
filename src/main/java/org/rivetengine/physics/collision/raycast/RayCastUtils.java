package org.rivetengine.physics.collision.raycast;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.rivetengine.core.Game;
import org.rivetengine.core.Scene;
import org.rivetengine.entity.Entity;
import org.rivetengine.entity.components.physics.collision.Collider;
import org.rivetengine.entity.components.physics.collision.Collider3d;
import org.rivetengine.entity.components.physics.collision.BoxCollider;
import org.rivetengine.physics.collision.BoundingBox;
import org.rivetengine.rendering.RenderUtils;
import org.rivetengine.window.GameWindow;

public class RayCastUtils {
    public static RayCastHit rayCast(Scene scene, Vector3f origin, Vector3f direction) {
        return new RayCast(scene, origin, direction).cast();
    }

    public static RayCastHit rayCast(Scene scene, Vector3f origin, Vector3f direction, float maxDistance) {
        return new RayCast(scene, origin, direction, maxDistance).cast();
    }

    public static Collider getIntersectingCollider(Scene scene, Vector3f point) {
        if (scene == null)
            return null;

        for (Entity entity : scene.getAllEntities()) {
            Collider3d collider = entity.getComponent(Collider3d.class);
            if (collider == null)
                continue;

            if (collider instanceof BoxCollider box) {
                BoundingBox boxBounds = box.getBox();
                if (boxBounds.containsPoint(point))
                    return collider;
            }
        }

        return null;
    }

    public static Vector3f screenToWorldPoint(Game game, float x, float y) {
        Vector2f normalizedCoords = screenToNormalisedDeviceCoords(x, y);
        Vector4f clipCoords = new Vector4f(normalizedCoords.x, normalizedCoords.y, -1f, 1f);
        Matrix4f[] matrices = RenderUtils.createCameraMatrices(game);
        Vector4f eyeCoords = clipToEyeCoords(clipCoords, matrices[0]);
        return eyeToWorldPoint(eyeCoords, matrices[1]);
    }

    public static Vector4f clipToEyeCoords(Vector4f clipCoords, Matrix4f projection) {
        Matrix4f invertedProjection = new Matrix4f(projection).invert();
        Vector4f eyeCoords = invertedProjection.transform(clipCoords, new Vector4f());
        return new Vector4f(eyeCoords.x, eyeCoords.y, -1f, 0f);
    }

    public static Vector3f eyeToWorldPoint(Vector4f eyeCoords, Matrix4f view) {
        Matrix4f invertedView = new Matrix4f(view).invert();
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
