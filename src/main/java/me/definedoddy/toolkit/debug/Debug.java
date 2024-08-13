package me.definedoddy.toolkit.debug;

import me.definedoddy.engine.rendering.shader.Shader;
import me.definedoddy.engine.rendering.shader.debug.DebugRenderer;
import me.definedoddy.engine.rendering.shader.debug.DebugShader;
import me.definedoddy.toolkit.debug.draw.Line;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class Debug {
    private static final DebugRenderer renderer = new DebugRenderer(DebugShader.create());
    private static final List<DebugHandler> handlers = new ArrayList<>();

    private static final List<Line> lines = new ArrayList<>();

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

    public static void stop() {
        renderer.stop();
    }

    public static void drawLine(Line line) {
        lines.add(line);
    }

    public static void render() {
        Shader shader = Shader.getCurrent();
        if (shader != null) shader.unbind();

        renderer.preRender();
        GL11.glBegin(GL11.GL_LINES);

        for (Line line : lines) {
            GL11.glLineWidth(line.getWidth());

            GL11.glColor3f(line.getColor().getRed() / 255f,
                    line.getColor().getGreen() / 255f,
                    line.getColor().getBlue() / 255f
            );

            GL11.glVertex3f(line.getStart().x, line.getStart().y, line.getStart().z);
            GL11.glVertex3f(line.getEnd().x, line.getEnd().y, line.getEnd().z);
        }

        GL11.glEnd();
        renderer.postRender();
        lines.clear();

        if (shader != null) shader.bind();
    }
}
