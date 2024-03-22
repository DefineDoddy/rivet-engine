package me.definedoddy.engine.utils.errors;

import static org.lwjgl.util.tinyfd.TinyFileDialogs.*;

public class ErrorWindowPopup extends RuntimeException {
    private final String title;

    public ErrorWindowPopup(String title, String message) {
        super(message);
        this.title = message;

        createErrorWindow(title, message);
    }

    public ErrorWindowPopup(String title, String message, Throwable cause) {
        super(message, cause);
        this.title = message;

        createErrorWindow(title, message);
    }

    protected void createErrorWindow(String title, String message) {
        tinyfd_messageBox(title, message, "ok", "error", false);
    }

    public String getTitle() {
        return title;
    }
}
