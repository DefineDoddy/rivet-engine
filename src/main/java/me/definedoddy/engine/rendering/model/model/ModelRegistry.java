package me.definedoddy.engine.rendering.model.model;

import me.definedoddy.engine.debug.Debug;

import java.util.HashMap;
import java.util.Map;

public class ModelRegistry {
    private static final Map<String, Model> models = new HashMap<>();

    public static void register(String id, Model model) {
        models.put(id, model);
        Debug.log("Registered model: " + id);
    }

    public static Model getModel(String id) {
        return models.get(id).clone();
    }
}
