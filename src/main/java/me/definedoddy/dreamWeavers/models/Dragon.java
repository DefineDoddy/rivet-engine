package me.definedoddy.dreamWeavers.models;

import me.definedoddy.engine.entity.ModelEntity;
import me.definedoddy.engine.rendering.object.model.Model;
import me.definedoddy.toolkit.file.Resource;
import me.definedoddy.toolkit.model.obj.importer.ModelImporter;

import java.awt.*;

public class Dragon extends ModelEntity {
    @Override
    protected Model defineModel() {
        Resource obj = new Resource("assets/obj/dragon.obj");
        Model model = ModelImporter.importModel(obj);
        model.getMaterial().setShininess(1f);
        model.getMaterial().setColour(Color.BLUE);
        return model;
    }
}
