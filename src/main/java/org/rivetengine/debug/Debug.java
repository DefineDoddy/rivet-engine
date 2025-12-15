package org.rivetengine.debug;

import org.rivetengine.debug.draw.Cube;
import org.rivetengine.debug.draw.Line;

import java.util.ArrayList;
import java.util.List;

public class Debug {
    private static final List<DebugHandler> handlers = new ArrayList<>();

    static final List<Line> lines = new ArrayList<>();
    static final List<Cube> cubes = new ArrayList<>();

    public static void logDebug(String message, Object... args) {
        System.out.println("[DEBUG] " + String.format(message, args));
    }

    public static void logInfo(String message, Object... args) {
        System.out.println("[INFO] " + String.format(message, args));
    }

    public static void logWarn(String message, Object... args) {
        System.out.println("[WARNING] " + String.format(message, args));
    }

    public static void logWarn(String message, Throwable throwable) {
        System.out.println("[WARNING] " + message);
        throwable.printStackTrace();
    }

    public static void logError(String message, Object... args) {
        System.out.println("[ERROR] " + String.format(message, args));
    }

    public static void logError(String message, Throwable throwable) {
        System.out.println("[ERROR] " + message);
        throwable.printStackTrace();
    }

    static void registerHandler(DebugHandler handler) {
        handlers.add(handler);
    }

    static void unregisterHandler(DebugHandler handler) {
        handlers.remove(handler);
    }

    public static void setRenderNormals(boolean renderNormals) {
        handlers.forEach(handler -> handler.setRenderNormals(renderNormals));
    }

    public static void setWireframe(boolean wireframe) {
        handlers.forEach(handler -> handler.setWireframe(wireframe));
    }

    public static void setRenderBoundingBoxes(boolean renderBoundingBoxes) {
        handlers.forEach(handler -> handler.setRenderBoundingBoxes(renderBoundingBoxes));
    }

    public static void drawLine(Line line) {
        lines.add(line);
    }

    public static void drawCube(Cube cube) {
        cubes.add(cube);
    }
}
