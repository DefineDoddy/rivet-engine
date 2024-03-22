package me.definedoddy.toolkit.program;

public class Program {
    public static void exitWithError(String message) {
        System.err.println(message);
        System.exit(1);
    }

    public static void exitWithError(String message, Throwable cause) {
        System.err.println(message);
        cause.printStackTrace();
        System.exit(1);
    }

    public static void exitWithError(Throwable cause) {
        cause.printStackTrace();
        System.exit(1);
    }

    public static void exitWithError() {
        System.exit(1);
    }

    public static void exit() {
        System.exit(0);
    }
}
