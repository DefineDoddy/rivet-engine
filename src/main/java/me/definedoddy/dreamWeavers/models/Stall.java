package me.definedoddy.dreamWeavers.models;

import me.definedoddy.engine.entity.ModelEntity;
import me.definedoddy.engine.rendering.object.model.Model;
import me.definedoddy.engine.rendering.object.model.ModelLoader;
import me.definedoddy.toolkit.file.Resource;

public class Stall extends ModelEntity {
    @Override
    protected Model defineModel() {
        Resource obj = new Resource("obj/stall.obj");
        Resource texture = new Resource("obj/stallTexture.png");

        return ModelLoader.loadFromObjFile(obj, texture);
    }
}
