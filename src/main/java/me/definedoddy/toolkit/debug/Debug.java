package me.definedoddy.toolkit.debug;

import me.definedoddy.engine.rendering.shader.Shader;
import me.definedoddy.engine.rendering.shader.debug.DebugRenderer;
import me.definedoddy.engine.rendering.shader.debug.DebugShader;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Debug {
    private static final DebugRenderer renderer = new DebugRenderer(DebugShader.create());
    private static final List<DebugHandler> handlers = new ArrayList<>();

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

    public static void drawLine(Vector3f start, Vector3f end) {
        drawLine(start, end, Color.WHITE);
    }

    public static void drawLine(Vector3f start, Vector3f end, Color colour) {
        Shader shader = Shader.getCurrent();

        shader.unbind();
        renderer.preRender();

        GL11.glLineWidth(2);
        GL11.glBegin(GL11.GL_LINES);

        GL11.glColor3f(colour.getRed() / 255f, colour.getGreen() / 255f, colour.getBlue() / 255f);

        GL11.glVertex3f(start.x, start.y, start.z);
        GL11.glVertex3f(end.x, end.y, end.z);

        GL11.glEnd();
        renderer.postRender();
        shader.bind();
    }
}
