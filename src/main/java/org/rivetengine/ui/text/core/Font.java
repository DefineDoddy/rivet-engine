package org.rivetengine.ui.text.core;

import org.rivetengine.rendering.texture.Texture;

public class Font {
    private final Texture fontAtlas;

    public Font(Texture fontAtlas) {
        this.fontAtlas = fontAtlas;
    }

    public Texture getFontAtlas() {
        return fontAtlas;
    }
}
