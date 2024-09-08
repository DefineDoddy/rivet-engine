package me.definedoddy.engine.ui.text.core;

import me.definedoddy.engine.rendering.texture.Texture;

public class Font {
    private final Texture fontAtlas;

    public Font(Texture fontAtlas) {
        this.fontAtlas = fontAtlas;
    }

    public Texture getFontAtlas() {
        return fontAtlas;
    }
}
