package org.rivetengine.rendering;

import java.util.List;

import org.rivetengine.core.Game;
import org.rivetengine.entity.Entity;
import org.rivetengine.entity.components.camera.Camera;
import org.rivetengine.entity.components.camera.CameraShake;
import org.rivetengine.system.GameSystem;
import org.rivetengine.utils.Noise;

public class CameraSystem extends GameSystem {
    @Override
    public void update(Game game, float dt) {
        List<Entity> entities = game.getActiveScene().getAllEntities();

        for (Entity entity : entities) {
            Camera camera = entity.getComponent(Camera.class);
            if (camera == null || !camera.active) { // TODO: implement .isMain?
                continue;
            }

            CameraShake shake = entity.getComponent(CameraShake.class);
            if (shake == null) {
                continue;
            }

            if (shake.isActive()) {
                float elapsed = shake.elapsed + dt;
                shake.elapsed = elapsed;

                float decay = Math.max(0, 1f - (elapsed / shake.duration));
                float freq = shake.frequency;
                float seed = shake.seed;

                shake.offset.set(
                        (Noise.noise(seed + elapsed * freq) * 2f - 1f) * shake.positionIntensity.x * decay,
                        (Noise.noise(seed + 1000f + elapsed * freq * 1.1f) * 2f - 1f) * shake.positionIntensity.y
                                * decay,
                        (Noise.noise(seed + 2000f + elapsed * freq * 1.2f) * 2f - 1f) * shake.positionIntensity.z
                                * decay);

                shake.rotationOffset.set(
                        (Noise.noise(seed + 3000f + elapsed * freq * 0.9f) * 2f - 1f) * shake.rotationIntensity.x
                                * decay,
                        (Noise.noise(seed + 4000f + elapsed * freq * 1.0f) * 2f - 1f) * shake.rotationIntensity.y
                                * decay,
                        (Noise.noise(seed + 5000f + elapsed * freq * 1.1f) * 2f - 1f) * shake.rotationIntensity.z
                                * decay);
            } else {
                shake.offset.set(0, 0, 0);
                shake.rotationOffset.set(0, 0, 0);
            }
        }
    }
}
