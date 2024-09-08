package me.definedoddy.dreamWeavers.models;

import me.definedoddy.engine.rendering.model.model.Model;
import me.definedoddy.engine.rendering.model.model.ModelRegistry;
import me.definedoddy.toolkit.file.Resource;
import me.definedoddy.toolkit.model.obj.importer.ModelImporter;

public class Models {
    public static void registerAll() {
        ModelRegistry.register("stall", fromResource("assets/obj/stall/stall.obj"));
        ModelRegistry.register("dragon", fromResource("assets/obj/dragon.obj"));
    }

    private static Model fromResource(String path) {
        return ModelImporter.importModel(new Resource(path));
    }
}
