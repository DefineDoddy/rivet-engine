package me.definedoddy.game.system;

import me.definedoddy.game.component.PlayerController;
import org.joml.Vector3f;
import org.rivetengine.core.Game;
import org.rivetengine.core.Scene;
import org.rivetengine.entity.Entity;
import org.rivetengine.entity.components.Shaker;
import org.rivetengine.entity.components.Transform;
import org.rivetengine.entity.components.physics.body.Rigidbody;
import org.rivetengine.entity.components.physics.collision.BoxCollider;
import org.rivetengine.system.GameSystem;
import org.rivetengine.system.SystemUtils;
import org.rivetengine.toolkit.maths.Directions;

public class PlayerActionSystem extends GameSystem {
    @Override
    public void update(Game game, float dt) {
        Scene scene = game.getActiveScene();
        for (Entity entity : SystemUtils.getEntitiesWithComponent(game, PlayerController.class)) {
            PlayerController controller = entity.getComponent(PlayerController.class);
            Rigidbody body = entity.getComponent(Rigidbody.class);
            Transform transform = entity.getComponent(Transform.class);
            BoxCollider collider = entity.getComponent(BoxCollider.class);
            Entity cameraEntity = entity.getChild("Main Camera");
            Shaker shaker = cameraEntity != null ? cameraEntity.getComponent(Shaker.class) : null;

            boolean wasGrounded = controller.isGrounded;

            if (body == null || transform == null) {
                continue;
            }

            float halfHeight = collider != null ? (collider.size.y - collider.offset.y) : 1f;
            Vector3f rayOrigin = new Vector3f(transform.position).add(0, -halfHeight + 0.1f, 0);
            controller.isGrounded = scene.rayCast(rayOrigin, Directions.DOWN, 0.2f, entity) != null;
            boolean justLanded = !wasGrounded && controller.isGrounded && body.velocity.y < -2f;

            if (justLanded && shaker != null) {
                shaker.shake(new Vector3f(0.05f, 0.08f, 0.05f), new Vector3f(0.35f, 0.2f, 0.1f), 0.25f, 32f);
            }

            if (controller.jumpRequested && controller.isGrounded) {
                body.velocity.y = controller.jumpForce;
                controller.isSliding = false;
                controller.isDashing = false;

                if (shaker != null) {
                    shaker.shake(new Vector3f(0.02f, 0.02f, 0.02f), new Vector3f(0.2f, 0.1f, 0.05f), 0.2f, 30f);
                }
            }

            controller.dashTimer = Math.max(0f, controller.dashTimer - dt);
            if (controller.dashRequested && controller.dashTimer <= 0f) {
                Vector3f dashDir = new Vector3f(controller.moveInput);
                if (dashDir.lengthSquared() == 0f) {
                    dashDir.set((float) Math.sin(Math.toRadians(transform.rotation.y)), 0,
                            (float) Math.cos(Math.toRadians(transform.rotation.y))).negate();
                }
                dashDir.normalize();
                Vector3f dashVel = new Vector3f(dashDir).mul(controller.dashForce);
                body.velocity.x = dashVel.x;
                body.velocity.z = dashVel.z;

                controller.dashTimer = controller.dashCooldown;
                controller.dashTimeLeft = controller.dashDuration;
                controller.isDashing = true;

                if (shaker != null) {
                    shaker.shake(new Vector3f(0.04f, 0.04f, 0.04f), new Vector3f(0.5f, 0.4f, 0.2f), 0.2f, 40f);
                }
            }

            if (controller.isDashing) {
                controller.dashTimeLeft -= dt;
                if (controller.dashTimeLeft <= 0f) {
                    controller.isDashing = false;
                }
            }

            if (controller.slideRequested && controller.isGrounded && !controller.isSliding) {
                Vector3f slideDir = new Vector3f(controller.moveInput);
                if (slideDir.lengthSquared() == 0f) {
                    slideDir.set((float) Math.sin(Math.toRadians(transform.rotation.y)), 0,
                            (float) Math.cos(Math.toRadians(transform.rotation.y))).negate();
                }
                slideDir.normalize();

                Vector3f slideKick = new Vector3f(slideDir).mul(controller.slideBoost);
                body.velocity.x += slideKick.x;
                body.velocity.z += slideKick.z;

                controller.isSliding = true;

                if (shaker != null) {
                    shaker.shake(new Vector3f(0.06f, 0.05f, 0.08f), new Vector3f(0.4f, 0.3f, 0.25f), 0.25f, 28f);
                }
            }

            if (!controller.slideRequested || !controller.isGrounded) {
                controller.isSliding = false;
            }

            float targetHeight = controller.isSliding ? controller.slidingHeight : controller.standingHeight;
            if (collider != null && collider.size.y != targetHeight / 2f) {
                collider.size.y = targetHeight / 2f;
                collider.offset.y = controller.isSliding ? -collider.size.y : 0f;
            }
            controller.cameraOffsetY = controller.isSliding ? 0.2f : 0.8f;
            controller.wasGrounded = controller.isGrounded;

            controller.dashRequested = false;
            controller.jumpRequested = false;
        }
    }
}
