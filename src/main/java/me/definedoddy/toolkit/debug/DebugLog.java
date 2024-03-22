package me.definedoddy.toolkit.debug;

public class DebugLog {
    public static void info(String message) {
        System.out.println("[INFO] " + message);
    }

    public static void info(String message, Object... args) {
        System.out.println("[INFO] " + String.format(message, args));
    }

    public static void warning(String message) {
        System.out.println("[WARNING] " + message);
    }

    public static void warning(String message, Throwable throwable) {
        System.out.println("[WARNING] " + message);
        throwable.printStackTrace();
    }

    public static void warning(String message, Object... args) {
        System.out.println("[WARNING] " + String.format(message, args));
    }

    public static void error(String message) {
        System.out.println("[ERROR] " + message);
    }

    public static void error(String message, Throwable throwable) {
        System.out.println("[ERROR] " + message);
        throwable.printStackTrace();
    }

    public static void error(String message, Object... args) {
        System.out.println("[ERROR] " + String.format(message, args));
    }
}
