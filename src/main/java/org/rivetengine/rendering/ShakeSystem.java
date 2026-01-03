package org.rivetengine.rendering;

import java.util.List;

import org.rivetengine.core.Game;
import org.rivetengine.entity.Entity;
import org.rivetengine.entity.components.Shaker;
import org.rivetengine.system.GameSystem;
import org.rivetengine.system.SystemUtils;
import org.rivetengine.utils.Noise;

public class ShakeSystem extends GameSystem {
    @Override
    public void update(Game game, float dt) {
        List<Entity> entities = SystemUtils.getEntitiesWithComponent(game, Shaker.class);

        for (Entity entity : entities) {
            Shaker shake = entity.getComponent(Shaker.class);

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
            } else if (shake.offset.lengthSquared() > 0 || shake.rotationOffset.lengthSquared() > 0) {
                shake.offset.set(0, 0, 0);
                shake.rotationOffset.set(0, 0, 0);
            }
        }
    }
}
