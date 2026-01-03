package me.definedoddy.game.system;

import me.definedoddy.game.component.PlayerController;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.rivetengine.core.Game;
import org.rivetengine.core.Scene;
import org.rivetengine.entity.Entity;
import org.rivetengine.entity.components.Transform;
import org.rivetengine.input.Input;
import org.rivetengine.input.KeyCode;
import org.rivetengine.rendering.RenderUtils;
import org.rivetengine.system.GameSystem;

public class PlayerInputSystem extends GameSystem {
    @Override
    public void update(Game game, float dt) {
        Scene scene = game.getActiveScene();
        Entity player = scene.getEntity("Player");

        if (player == null) {
            return;
        }

        PlayerController controller = player.getComponent(PlayerController.class);
        Transform playerTransform = player.getComponent(Transform.class);
        Entity cameraEntity = player.getChild("Main Camera");

        if (cameraEntity == null || controller == null) {
            return;
        }

        Transform cameraTransform = cameraEntity.getComponent(Transform.class);

        Vector2f mouseDelta = Input.mouse.getDelta();
        float sensitivity = 0.1f;
        playerTransform.rotation.y -= mouseDelta.x * sensitivity;
        cameraTransform.rotation.x = Math.max(-90,
                Math.min(90, cameraTransform.rotation.x - mouseDelta.y * sensitivity));

        Matrix4f cameraWorld = RenderUtils.getWorldMatrixSafe(cameraEntity);
        Vector3f forward = new Vector3f(0, 0, -1);
        cameraWorld.transformDirection(forward);
        forward.y = 0;
        forward.normalize();
        Vector3f right = new Vector3f(forward).cross(0, 1, 0).normalize();

        float forwardAxis = (Input.keyboard.isKeyPressed(KeyCode.W) ? 1f : 0f)
                - (Input.keyboard.isKeyPressed(KeyCode.S) ? 1f : 0f);
        float strafeAxis = (Input.keyboard.isKeyPressed(KeyCode.D) ? 1f : 0f)
                - (Input.keyboard.isKeyPressed(KeyCode.A) ? 1f : 0f);

        controller.rawMoveInput.set(strafeAxis, 0, -forwardAxis);
        controller.moveInput.set(forward.mul(forwardAxis).add(right.mul(strafeAxis)));
        if (controller.moveInput.lengthSquared() > 0f) {
            controller.moveInput.normalize();
        }

        controller.jumpRequested = Input.keyboard.wasKeyPressed(KeyCode.SPACE);
        controller.dashRequested = Input.keyboard.wasKeyPressed(KeyCode.LEFT_SHIFT);
        controller.slideRequested = Input.keyboard.isKeyPressed(KeyCode.LEFT_CONTROL);
    }
}