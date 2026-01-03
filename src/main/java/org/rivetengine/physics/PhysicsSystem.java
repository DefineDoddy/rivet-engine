package org.rivetengine.physics;

import org.rivetengine.core.Game;
import org.rivetengine.entity.Entity;
import org.rivetengine.entity.components.Transform;
import org.rivetengine.entity.components.physics.body.PhysicsBody;
import org.rivetengine.entity.components.physics.body.KinematicBody;
import org.rivetengine.entity.components.physics.body.Rigidbody;
import org.rivetengine.entity.components.physics.collision.Collider;
import org.rivetengine.system.GameSystem;
import org.rivetengine.system.SystemUtils;

import java.util.List;

public class PhysicsSystem extends GameSystem {
    private final PhysicsResolver resolver = new PhysicsResolver();
    private float accumTime = 0f;

    @Override
    public void update(Game game, float dt) {
        accumTime += dt;

        if (accumTime < Physics.TIME_STEP) {
            return;
        }

        List<Entity> collidables = SystemUtils.getEntitiesWithComponent(game, Collider.class);
        List<Entity> bodies = SystemUtils.getEntitiesWithComponent(game, PhysicsBody.class);

        while (accumTime >= Physics.TIME_STEP) {
            integrate(bodies, Physics.TIME_STEP);
            resolver.resolve(collidables);
            accumTime -= Physics.TIME_STEP;
        }
    }

    private void integrate(List<Entity> entities, float dt) {
        for (Entity entity : entities) {
            PhysicsBody body = entity.getComponent(PhysicsBody.class);

            if (body instanceof Rigidbody rb) {
                rb.velocity.fma(dt, Physics.gravity);
                Transform transform = SystemUtils.getTransformSafe(entity);
                transform.position.fma(dt, rb.velocity);
            } else if (body instanceof KinematicBody kb) {
                Transform transform = SystemUtils.getTransformSafe(entity);
                transform.position.fma(dt, kb.velocity);
            }
        }
    }
}