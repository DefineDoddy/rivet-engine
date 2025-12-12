package me.definedoddy.engine.ui;

import me.definedoddy.engine.core.Engine;

public class UIBlock extends UIComponent {
    public UIBlock() {
        Engine.getRenderer().getUIRenderer().addBlock(this);
    }
}
