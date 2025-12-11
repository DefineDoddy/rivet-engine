package me.definedoddy.engine.entity.components;

import me.definedoddy.engine.entity.Component;

public class Tags implements Component {
    private final String[] tags;

    public Tags(String... tags) {
        this.tags = tags;
    }

    public String[] getTags() {
        return tags;
    }

    public boolean hasTag(String tag) {
        for (String t : tags) {
            if (t.equals(tag)) {
                return true;
            }
        }
        return false;
    }
}
