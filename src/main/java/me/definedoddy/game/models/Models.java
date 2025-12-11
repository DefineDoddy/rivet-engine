package me.definedoddy.game.models;

import me.definedoddy.engine.rendering.model.model.Model;
import me.definedoddy.toolkit.file.Resource;
import me.definedoddy.toolkit.model.obj.importer.ModelImporter;

public class Models {
    public static void registerAll() {

    }

    private static Model fromResource(String path) {
        return ModelImporter.importModel(new Resource(path));
    }
}
