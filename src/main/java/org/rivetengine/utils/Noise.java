package org.rivetengine.utils;

public class Noise {
    public static float noise(float x) {
        int i = (int) Math.floor(x);
        float f = x - i;
        f = f * f * (3.0f - 2.0f * f);
        return lerp(hash(i), hash(i + 1), f);
    }

    private static float hash(int x) {
        x = ((x >> 16) ^ x) * 0x45d9f3b;
        x = ((x >> 16) ^ x) * 0x45d9f3b;
        x = (x >> 16) ^ x;
        return (x & 0x7fffffff) / (float) 0x7fffffff;
    }

    private static float lerp(float a, float b, float t) {
        return a + t * (b - a);
    }
}
