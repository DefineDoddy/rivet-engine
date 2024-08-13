package me.definedoddy.dreamWeavers.models;

import me.definedoddy.engine.rendering.object.model.Model;
import me.definedoddy.engine.rendering.object.model.ModelRegistry;
import me.definedoddy.toolkit.file.Resource;
import me.definedoddy.toolkit.model.obj.importer.ModelImporter;

public class Models {
    public static void registerAll() {
        ModelRegistry.register("stall", importModelFromResource("assets/obj/stall/stall.obj"));
        ModelRegistry.register("dragon", importModelFromResource("assets/obj/dragon.obj"));
    }

    private static Model importModelFromResource(String path) {
        return ModelImporter.importModel(new Resource(path));
    }
}
