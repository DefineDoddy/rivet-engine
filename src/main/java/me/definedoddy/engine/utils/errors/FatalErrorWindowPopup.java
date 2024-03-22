package me.definedoddy.engine.utils.errors;

import me.definedoddy.toolkit.program.Program;

public class FatalErrorWindowPopup extends ErrorWindowPopup {

    public FatalErrorWindowPopup(String title, String message) {
        super(title, message);
    }

    public FatalErrorWindowPopup(String title, String message, Throwable cause) {
        super(title, message, cause);
    }

    @Override
    protected void createErrorWindow(String title, String message) {
        super.createErrorWindow(title, message);
        Program.exitWithError();
    }
}
