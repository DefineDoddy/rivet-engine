package me.definedoddy.engine.input;

public enum MouseBtn {
    LEFT(0),
    RIGHT(1),
    MIDDLE(2);

    private final int id;

    MouseBtn(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
