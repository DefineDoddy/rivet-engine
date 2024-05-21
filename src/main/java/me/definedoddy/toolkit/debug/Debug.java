package me.definedoddy.toolkit.debug;

public class Debug {
    public static void log(String message) {
        System.out.println("[INFO] " + message);
    }

    public static void log(String message, Object... args) {
        System.out.println("[INFO] " + String.format(message, args));
    }

    public static void logWarning(String message) {
        System.out.println("[WARNING] " + message);
    }

    public static void logWarning(String message, Throwable throwable) {
        System.out.println("[WARNING] " + message);
        throwable.printStackTrace();
    }

    public static void logWarning(String message, Object... args) {
        System.out.println("[WARNING] " + String.format(message, args));
    }

    public static void logError(String message) {
        System.out.println("[ERROR] " + message);
    }

    public static void logError(String message, Throwable throwable) {
        System.out.println("[ERROR] " + message);
        throwable.printStackTrace();
    }

    public static void logError(String message, Object... args) {
        System.out.println("[ERROR] " + String.format(message, args));
    }
}
