package org.rivetengine.ui;

public class UI {
    private static final UIContainer container = new UIContainer();

    private static int width = 1920;
    private static int height = 1080;

    public static void init() {
        // container.addChild(new UIBlock());
    }

    public static void update() {
        container.update();
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public static void setWidth(int width) {
        UI.width = width;
    }

    public static void setHeight(int height) {
        UI.height = height;
    }

    public static UIContainer getContainer() {
        return container;
    }
}
