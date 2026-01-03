package me.definedoddy.game.prefabs;

import org.rivetengine.core.Assets;
import org.rivetengine.entity.Entity;
import org.rivetengine.entity.Prefab;
import org.rivetengine.entity.components.Shaker;
import org.rivetengine.entity.components.Transform;
import org.rivetengine.entity.components.camera.Camera;
import org.rivetengine.entity.components.camera.PostProcessing;
import org.rivetengine.entity.components.camera.Skybox;
import org.rivetengine.rendering.cubemap.CubeMap;
import org.rivetengine.rendering.postprocessing.Bloom;
import org.rivetengine.toolkit.memory.Handle;

public class CameraPrefab implements Prefab {
    @Override
    public Entity create() {
        Entity camera = new Entity("Main Camera");

        camera.addComponent(Transform.fromXYZ(0, 0.8f, 0));
        camera.addComponent(new Camera());
        camera.addComponent(new Shaker());

        PostProcessing postProcessing = new PostProcessing();
        postProcessing.addEffect(new Bloom());
        camera.addComponent(postProcessing);

        Handle<CubeMap> sky = Assets.load("assets/skybox", CubeMap.class);
        camera.addComponent(new Skybox(sky));

        return camera;
    }
}
