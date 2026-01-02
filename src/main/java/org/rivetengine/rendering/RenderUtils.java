package org.rivetengine.rendering;

import org.joml.Matrix4f;
import org.rivetengine.core.Game;
import org.rivetengine.entity.Entity;
import org.rivetengine.entity.components.Transform;
import org.rivetengine.entity.components.camera.Camera;
import org.rivetengine.entity.components.camera.CameraShake;

public class RenderUtils {
    public static Matrix4f[] createCameraMatrices(Game game) {
        Entity cameraEntity = getActiveCameraEntity(game);

        if (cameraEntity == null) {
            return new Matrix4f[] { new Matrix4f(), new Matrix4f() };
        }

        Matrix4f projectionMatrix = createProjectionMatrix(game, cameraEntity);
        Matrix4f viewMatrix = createViewMatrix(cameraEntity);

        return new Matrix4f[] { projectionMatrix, viewMatrix };
    }

    public static Entity getActiveCameraEntity(Game game) {
        return game.getActiveScene().getAllEntities().stream()
                .filter(e -> e.hasComponent(Camera.class))
                .findFirst()
                .orElse(null);
    }

    public static Matrix4f createProjectionMatrix(Game game, Entity cameraEntity) {
        Camera camera = cameraEntity.getComponent(Camera.class);
        float aspectRatio = (float) game.window.getWidth() / game.window.getHeight();

        return new Matrix4f().perspective((float) Math.toRadians(camera.fieldOfView), aspectRatio,
                camera.nearPlane, camera.farPlane);
    }

    public static Matrix4f createViewMatrix(Entity cameraEntity) {
        Matrix4f worldMatrix = getWorldMatrixSafe(cameraEntity);
        CameraShake shake = cameraEntity.getComponent(CameraShake.class);
        if (shake != null) {
            worldMatrix.translate(shake.offset);
            worldMatrix.rotateXYZ(
                    (float) Math.toRadians(shake.rotationOffset.x),
                    (float) Math.toRadians(shake.rotationOffset.y),
                    (float) Math.toRadians(shake.rotationOffset.z));
        }
        return worldMatrix.invert(new Matrix4f());
    }

    public static Matrix4f getWorldMatrixSafe(Entity entity) {
        Transform transform = entity.getComponent(Transform.class);

        if (transform == null) {
            transform = new Transform();
        }

        return transform.getWorldMatrix(entity);
    }
}
