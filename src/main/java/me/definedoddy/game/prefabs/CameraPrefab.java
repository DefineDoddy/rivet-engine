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
import org.rivetengine.rendering.postprocessing.chromaticaberration.ChromaticAberration;
import org.rivetengine.rendering.postprocessing.vignette.Vignette;
import org.rivetengine.toolkit.memory.Handle;

public class CameraPrefab implements Prefab {
    @Override
    public Entity create() {
        Entity entity = new Entity("Main Camera");

        entity.addChild(new GunPrefab().create());

        entity.addComponent(Transform.fromXYZ(0, 0.8f, 0));
        entity.addComponent(new Shaker());

        Camera camera = new Camera();
        camera.fieldOfView = 80f;
        entity.addComponent(camera);

        PostProcessing postProcessing = new PostProcessing();
        postProcessing.addEffect(new Vignette());
        postProcessing.addEffect(new ChromaticAberration());
        entity.addComponent(postProcessing);

        Handle<CubeMap> sky = Assets.load("assets/skybox", CubeMap.class);
        entity.addComponent(new Skybox(sky));

        return entity;
    }
}
