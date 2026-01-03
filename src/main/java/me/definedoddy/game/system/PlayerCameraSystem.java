package me.definedoddy.game.system;

import me.definedoddy.game.component.PlayerController;
import org.rivetengine.core.Game;
import org.rivetengine.entity.Entity;
import org.rivetengine.entity.components.Transform;
import org.rivetengine.entity.components.camera.Camera;
import org.rivetengine.entity.components.physics.body.Rigidbody;
import org.rivetengine.system.GameSystem;
import org.rivetengine.system.SystemUtils;

public class PlayerCameraSystem extends GameSystem {
    private float baseFov = 70f;
    private float dashFov = 90f;
    private float slideFov = 80f;
    private float fovLerpSpeed = 10f;

    @Override
    public void update(Game game, float dt) {
        for (Entity entity : SystemUtils.getEntitiesWithComponent(game, PlayerController.class)) {
            PlayerController controller = entity.getComponent(PlayerController.class);
            Entity cameraEntity = entity.getChild("Main Camera");
            if (cameraEntity == null) {
                continue;
            }

            Camera camera = cameraEntity.getComponent(Camera.class);
            Transform cameraTransform = cameraEntity.getComponent(Transform.class);
            Rigidbody body = entity.getComponent(Rigidbody.class);
            if (camera == null || cameraTransform == null) {
                continue;
            }

            // FOV Effect
            float targetFov = controller.isDashing ? dashFov : controller.isSliding ? slideFov : baseFov;
            camera.fieldOfView = lerp(camera.fieldOfView, targetFov, fovLerpSpeed * dt);

            float targetTilt = -controller.rawMoveInput.x * 2f;
            cameraTransform.rotation.z = lerp(cameraTransform.rotation.z, targetTilt, 5f * dt);

            float horizontalSpeed = 0f;
            if (body != null) {
                horizontalSpeed = (float) Math
                        .sqrt(body.velocity.x * body.velocity.x + body.velocity.z * body.velocity.z);
            }

            float bobWeight = (controller.isGrounded && !controller.isSliding) ? Math.min(1f,
                    horizontalSpeed / controller.walkSpeed) : 0f;

            if (bobWeight > 0.05f) {
                controller.bobPhase += dt * controller.bobFrequency * (0.5f + 0.5f * bobWeight);
                if (controller.bobPhase > Math.PI * 2f) {
                    controller.bobPhase -= (float) (Math.PI * 2f);
                }

                float targetBob = (float) Math.sin(controller.bobPhase) * controller.bobAmplitude * bobWeight;
                controller.bobOffset = lerp(controller.bobOffset, targetBob, controller.bobSmoothing * dt);
            } else {
                controller.bobPhase = 0f;
                controller.bobOffset = lerp(controller.bobOffset, 0f, controller.bobSmoothing * dt);
            }

            float targetY = controller.cameraOffsetY + controller.bobOffset;
            cameraTransform.position.y = lerp(cameraTransform.position.y, targetY, controller.cameraLerpSpeed * dt);
        }
    }

    private float lerp(float a, float b, float f) {
        return a + f * (b - a);
    }
}
