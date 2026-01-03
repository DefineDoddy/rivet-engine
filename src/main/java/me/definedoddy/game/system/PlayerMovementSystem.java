package me.definedoddy.game.system;

import me.definedoddy.game.component.PlayerController;
import org.joml.Vector3f;
import org.rivetengine.core.Game;
import org.rivetengine.entity.Entity;
import org.rivetengine.entity.components.physics.body.Rigidbody;
import org.rivetengine.system.GameSystem;
import org.rivetengine.system.SystemUtils;

public class PlayerMovementSystem extends GameSystem {
    @Override
    public void update(Game game, float dt) {
        for (Entity entity : SystemUtils.getEntitiesWithComponent(game, PlayerController.class)) {
            PlayerController controller = entity.getComponent(PlayerController.class);
            Rigidbody body = entity.getComponent(Rigidbody.class);

            if (body == null) {
                continue;
            }

            Vector3f vel = new Vector3f(body.velocity.x, 0, body.velocity.z);
            float moveLen = controller.moveInput.length();

            float accel = controller.isGrounded ? controller.acceleration
                    : controller.acceleration * controller.airControl;
            float friction = controller.isGrounded ? controller.friction : controller.friction * 1.5f;

            if (controller.isSliding) {
                friction = controller.slideFriction;
                accel = 0f;
            }

            float speed = vel.length();

            if (speed > 0f) {
                float drop = friction * dt;

                if (!controller.isSliding && controller.isGrounded && moveLen > 0f) {
                    Vector3f normVel = new Vector3f(vel).normalize();
                    float dot = normVel.dot(controller.moveInput) / moveLen; // cosine between velocity and input

                    if (dot < 0.8f) {
                        drop += controller.acceleration * 1.5f * (1f - dot) * dt;
                    }
                }

                float newSpeed = Math.max(0f, speed - drop);

                if (!controller.isSliding && moveLen == 0f && newSpeed < 0.35f) {
                    newSpeed = 0f;
                }

                if (speed > 0f) {
                    vel.mul(newSpeed / speed);
                }

                if (controller.isSliding) {
                    float decay = Math.max(0f, 1f - controller.slideDecay * dt);
                    vel.mul(decay);
                }
            }

            speed = vel.length();

            if (moveLen > 0f || controller.isSliding) {
                Vector3f wishDir;

                if (controller.isSliding) {
                    Vector3f slideInput = new Vector3f(controller.rawMoveInput.x, 0, -controller.rawMoveInput.z);
                    float slideLen = slideInput.length();

                    if (slideLen > 0f) {
                        wishDir = slideInput.div(slideLen);
                    } else {
                        wishDir = speed > 0f ? new Vector3f(vel).normalize() : new Vector3f(0, 0, 1);
                    }
                } else {
                    wishDir = new Vector3f(controller.moveInput).div(moveLen); // normalized
                }
                float wishSpeed = controller.walkSpeed;

                if (controller.isDashing) {
                    wishSpeed = Math.max(wishSpeed, controller.dashForce);
                }

                if (controller.isSliding) {
                    float dot = speed > 0f ? new Vector3f(vel).normalize().dot(wishDir) : 1f;

                    if (dot <= 0f) {
                        body.velocity.x = vel.x;
                        body.velocity.z = vel.z;
                        continue;
                    }

                    wishSpeed = Math.min(wishSpeed, vel.length());
                }

                float steeringDot = speed > 0f ? new Vector3f(vel).normalize().dot(wishDir) : 1f;
                float turnEase = 0.8f;

                if (steeringDot < 0f) {
                    turnEase = 0.15f;
                } else if (steeringDot < 0.5f) {
                    float t = steeringDot / 0.5f;
                    turnEase = 0.15f + (0.85f * t);
                }

                accel *= turnEase;

                float addSpeed = wishSpeed - vel.dot(wishDir);

                if (addSpeed > 0f) {
                    vel.fma(Math.min(addSpeed, accel * dt), wishDir);
                }
            }

            body.velocity.x = vel.x;
            body.velocity.z = vel.z;
        }
    }
}
