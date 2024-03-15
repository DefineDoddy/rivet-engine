package me.definedoddy.toolkit.errors;

import static org.lwjgl.util.tinyfd.TinyFileDialogs.*;

public class ErrorPopupException extends RuntimeException {
    private final String title;

    public ErrorPopupException(String title, String message) {
        super(message);
        this.title = message;

        createErrorWindow(title, message);
    }

    public ErrorPopupException(String title, String message, Throwable cause) {
        super(message, cause);
        this.title = message;

        createErrorWindow(title, message);
    }

    private void createErrorWindow(String title, String message) {
        tinyfd_messageBox(title, message, "ok", "error", false);
    }

    public String getTitle() {
        return title;
    }
}
