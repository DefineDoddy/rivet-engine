package me.definedoddy.dreamWeavers.models;

import me.definedoddy.engine.entity.ModelEntity;
import me.definedoddy.engine.rendering.object.model.Model;
import me.definedoddy.engine.rendering.object.model.ModelLoader;
import me.definedoddy.engine.rendering.texture.*;
import me.definedoddy.toolkit.file.Resource;

public class Stall extends ModelEntity {
    @Override
    protected Model defineModel() {
        Resource obj = new Resource("assets/obj/stall/stall.obj");

        Resource texPath = new Resource("assets/obj/stall/stallTexture.png");
        Texture texture = TextureLoader.loadTexture2D(texPath, TextureType.DIFFUSE);
        Material material = new MaterialBuilder().diffuse(texture).build();

        return ModelLoader.loadFromObjFile(obj, material);
    }
}
