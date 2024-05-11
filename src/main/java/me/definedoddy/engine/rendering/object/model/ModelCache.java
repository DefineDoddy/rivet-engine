package me.definedoddy.engine.rendering.object.model;

import java.util.HashMap;
import java.util.Map;

public class ModelCache {
    private static final ModelCache instance = new ModelCache();

    private final Map<String, Model> modelMap = new HashMap<>();

    public void addModel(String key, Model model) {
        modelMap.put(key, model);
    }

    public Model getModel(String key) {
        return modelMap.get(key);
    }

    public static ModelCache getInstance() {
        return instance;
    }
}
