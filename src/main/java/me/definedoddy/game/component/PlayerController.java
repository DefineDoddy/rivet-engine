package me.definedoddy.game.component;

import org.joml.Vector3f;
import org.rivetengine.entity.component.Component;

public class PlayerController implements Component {
    // Input state
    public final Vector3f moveInput = new Vector3f();
    public final Vector3f rawMoveInput = new Vector3f();
    public boolean jumpRequested = false;
    public boolean dashRequested = false;
    public boolean slideRequested = false;

    // Movement settings
    public float walkSpeed = 20f;
    public float acceleration = 400f;
    public float friction = 50f;
    public float airControl = 0.7f;
    public float jumpForce = 15f;

    // Dash settings
    public float dashForce = 35f;
    public float dashCooldown = 0.5f;
    public float dashTimer = 0f;

    // Slide settings
    public float slideFriction = 6f;
    public float slideDecay = 0.5f;
    public float slideBoost = 15f;
    public float standingHeight = 2f;
    public float slidingHeight = 1f;
    public float cameraLerpSpeed = 10f;
    public float cameraOffsetY = 0.9f;

    // Camera bobbing
    public float bobAmplitude = 0.4f;
    public float bobFrequency = 22f;
    public float bobSmoothing = 12f;
    public float bobPhase = 0f;
    public float bobOffset = 0f;

    // State
    public boolean isGrounded = false;
    public boolean wasGrounded = false;
    public boolean isSliding = false;
    public boolean isDashing = false;
    public float dashDuration = 0.15f;
    public float dashTimeLeft = 0f;
}
