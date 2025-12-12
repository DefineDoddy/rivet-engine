package org.rivetengine.entity.components;

import me.definedoddy.engine.entity.component.Component;

public class Name implements Component {
    private String name;

    public Name(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}