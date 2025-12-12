package org.rivetengine.ui;

import org.rivetengine.core.Engine;

public class UIBlock extends UIComponent {
    public UIBlock() {
        Engine.getRenderer().getUIRenderer().addBlock(this);
    }
}
