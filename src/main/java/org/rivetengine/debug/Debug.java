package org.rivetengine.debug;

import org.rivetengine.debug.draw.Cube;
import org.rivetengine.debug.draw.Line;
import org.rivetengine.debug.shader.DebugRenderer;
import org.rivetengine.debug.shader.DebugShader;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class Debug {
    private static final DebugRenderer renderer = new DebugRenderer(DebugShader.create());
    private static final List<DebugHandler> handlers = new ArrayList<>();

    private static final List<Line> lines = new ArrayList<>();
    private static final List<Cube> cubes = new ArrayList<>();

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

    public static void stop() {
        renderer.stop();
    }

    public static void drawLine(Line line) {
        lines.add(line);
    }

    public static void drawCube(Cube cube) {
        cubes.add(cube);
    }

    public static void render() {
        renderer.preRender();
        renderLines();
        renderCubes();
        renderer.postRender();
    }

    private static void renderLines() {
        GL11.glBegin(GL11.GL_LINES);

        for (Line line : lines) line.draw();
        lines.clear();

        GL11.glEnd();
    }

    private static void renderCubes() {
        GL11.glBegin(GL11.GL_LINES);

        for (Cube cube : cubes) cube.draw();
        cubes.clear();

        GL11.glEnd();
    }
}
