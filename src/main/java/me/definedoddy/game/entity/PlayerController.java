package com.rivetengine.game.entity.player;

import com.rivetengine.engine.core.Time;
import com.rivetengine.engine.debug.Debug;
import me.definedoddy.engine.entity.EntityOld;
import com.rivetengine.engine.entity.components.physics.body.Rigidbody;
import com.rivetengine.engine.entity.components.physics.collision.BoxCollider;
import com.rivetengine.engine.entity.components.rendering.Camera;
import com.rivetengine.engine.input.KeyCode;
import com.rivetengine.engine.input.Keyboard;
import me.definedoddy.engine.scene.SceneManager;
import com.rivetengine.game.entity.player.camera.FPCamera;
import com.rivetengine.game.entity.player.camera.shake.CameraShake;
import org.joml.Vector3f;

public class PlayerController {
    private final Player player;

    public final float movementSpeed = 0.04f;
    public final float acceleration = 0.18f;

    public PlayerController(Player player) {
        this.player = player;
    }

    public void update() {
        calcMovement();
        calcVelocity();
    }

    private Vector3f velocity() {
        return player.getVelocity();
    }

    private Rigidbody rigidbody() {
        return player.getComponent(Rigidbody.class);
    }

    private BoxCollider collider() {
        return player.getComponent(BoxCollider.class);
    }

    private void calcMovement() {
        float forward = 0f, strafe = 0f, up = 0f;
        if (Keyboard.get().isKeyPressed(KeyCode.W))
            forward += 1f;
        if (Keyboard.get().isKeyPressed(KeyCode.S))
            forward -= 1f;
        if (Keyboard.get().isKeyPressed(KeyCode.A))
            strafe -= 1f;
        if (Keyboard.get().isKeyPressed(KeyCode.D))
            strafe += 1f;

        if (Keyboard.get().wasKeyPressed(KeyCode.SPACE) && isOnGround()) {
            jump();
            Debug.log("Jump");
            Camera.getActive(FPCamera.class).shake(new CameraShake(0.2f, 0.5f));
        }

        travel(forward, strafe, up);
    }

    private void travel(float forward, float strafe, float up) {
        if (forward == 0 && strafe == 0 && up == 0)
            return;

        float yawRad = (float) Math.toRadians(player.getCamera().getYaw());
        float x = (float) (strafe * Math.cos(yawRad) + forward * Math.sin(yawRad)) * (float) Time.getDeltaTime();
        float z = (float) (strafe * Math.sin(yawRad) - forward * Math.cos(yawRad)) * (float) Time.getDeltaTime();

        Vector3f vector = new Vector3f(x, up, z);
        vector.normalize().mul(movementSpeed);
        velocity().add(vector);

        if (velocity().lengthSquared() > 0) {
            float sine = (float) Math.sin(Math.toRadians(Time.getDeltaTime()));
            Camera.getActive().setPosition(new Vector3f(0f, 3.6f + sine, 0f));
        }
    }

    private void calcVelocity() {
        if (velocity().lengthSquared() > 0) {
            velocity().mul(1 - acceleration);
            if (velocity().lengthSquared() < 0.0001f)
                velocity().set(0);
        }

        move(velocity());
    }

    public void move(Vector3f vector) {
        player.setPosition(player.getPosition().add(vector));
    }

    public void jump() {
        rigidbody().addForce(new Vector3f(0, 12f, 0));
    }

    public boolean isOnGround() {
        for (EntityOld entity : SceneManager.getCurrentScene().getEntitiesWithTag("ground")) {
            if (collider().isCollidingWith(entity.getComponent(BoxCollider.class))) {
                return true;
            }
        }

        return false;
    }
}
