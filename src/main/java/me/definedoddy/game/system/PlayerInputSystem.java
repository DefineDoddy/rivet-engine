package me.definedoddy.game.system;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.rivetengine.core.Game;
import org.rivetengine.core.Scene;
import org.rivetengine.entity.Entity;
import org.rivetengine.entity.components.Transform;
import org.rivetengine.entity.components.camera.CameraShake;
import org.rivetengine.input.Input;
import org.rivetengine.input.KeyCode;
import org.rivetengine.rendering.RenderUtils;
import org.rivetengine.entity.components.physics.body.Rigidbody;
import org.rivetengine.system.GameSystem;

public class PlayerInputSystem extends GameSystem {
    @Override
    public void update(Game game, float dt) {
        Scene scene = game.getActiveScene();
        Entity player = scene.getEntity("Player");

        if (player == null) {
            return;
        }

        Transform playerTransform = player.getComponent(Transform.class);
        Rigidbody body = player.getComponent(Rigidbody.class);
        Entity cameraEntity = player.getChild("Main Camera");

        if (cameraEntity == null || body == null) {
            return;
        }

        Transform cameraTransform = cameraEntity.getComponent(Transform.class);

        // Mouse look
        Vector2f mouseDelta = Input.mouse.getDelta();
        float sensitivity = 0.1f;

        playerTransform.rotation.y -= mouseDelta.x * sensitivity;
        cameraTransform.rotation.x -= mouseDelta.y * sensitivity;
        cameraTransform.rotation.x = Math.max(-90, Math.min(90, cameraTransform.rotation.x));

        // Keyboard movement
        float speed = 15f;
        Vector3f moveDir = new Vector3f();

        Matrix4f cameraWorld = RenderUtils.getWorldMatrixSafe(cameraEntity);
        Vector3f forward = new Vector3f(0, 0, -1);
        cameraWorld.transformDirection(forward);
        forward.normalize();

        Vector3f right = new Vector3f(forward).cross(0, 1, 0).normalize();

        if (Input.keyboard.isKeyPressed(KeyCode.W)) {
            moveDir.add(forward);
        }
        if (Input.keyboard.isKeyPressed(KeyCode.S)) {
            moveDir.sub(forward);
        }
        if (Input.keyboard.isKeyPressed(KeyCode.A)) {
            moveDir.sub(right);
        }
        if (Input.keyboard.isKeyPressed(KeyCode.D)) {
            moveDir.add(right);
        }

        if (moveDir.length() > 0) {
            moveDir.normalize();
            body.velocity.set(moveDir.x * speed, body.velocity.y, moveDir.z * speed);
        } else {
            body.velocity.x = 0;
            body.velocity.z = 0;
        }

        if (Input.keyboard.wasKeyPressed(KeyCode.SPACE)) {
            CameraShake cameraShake = cameraEntity.getComponent(CameraShake.class);
            if (cameraShake != null) {
                cameraShake.shake(new Vector3f(0.2f, 0.2f, 0f), new Vector3f(0.2f, 0.2f, 0.5f), 0.5f);
            }
        }
    }
}