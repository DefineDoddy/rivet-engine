package me.definedoddy.engine.ui;

import me.definedoddy.engine.manager.GameManager;

public class UIBlock extends UIComponent {
    public UIBlock() {
        GameManager.getRenderEngine().getUIRenderer().addBlock(this);
    }
}
