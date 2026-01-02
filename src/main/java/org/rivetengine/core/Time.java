package org.rivetengine.core;

public class Time {
    private static double deltaTime;
    private static double lastFrameTime;

    private static double targetFps = 60;

    public static void init() {
        lastFrameTime = getTime();
    }

    public static void update() {
        double time = getTime();
        deltaTime = time - lastFrameTime;
        lastFrameTime = time;
    }

    public static double getTime() {
        return System.nanoTime() / 1000000000d;
    }

    public static double getDeltaTime() {
        return deltaTime;
    }

    public static double getFixedDeltaTime() {
        return 1 / targetFps;
    }

    public static double getFps() {
        return 1 / deltaTime;
    }

    public static double getTargetFps() {
        return targetFps;
    }

    public static void setTargetFps(double targetFps) {
        Time.targetFps = targetFps;
    }
}
