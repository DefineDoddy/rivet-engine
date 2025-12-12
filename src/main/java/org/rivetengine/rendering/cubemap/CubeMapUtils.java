package org.rivetengine.rendering.cubemap;

public class CubeMapUtils {
    public static float[] genCubeMapVertices(float size) {
        return new float[] {
                -size, size, -size,
                -size, -size, -size,
                size, -size, -size,
                size, -size, -size,
                size, size, -size,
                -size, size, -size,

                -size, -size, size,
                -size, -size, -size,
                -size, size, -size,
                -size, size, -size,
                -size, size, size,
                -size, -size, size,

                size, -size, -size,
                size, -size, size,
                size, size, size,
                size, size, size,
                size, size, -size,
                size, -size, -size,

                -size, -size, size,
                -size, size, size,
                size, size, size,
                size, size, size,
                size, -size, size,
                -size, -size, size,

                -size, size, -size,
                size, size, -size,
                size, size, size,
                size, size, size,
                -size, size, size,
                -size, size, -size,

                -size, -size, -size,
                -size, -size, size,
                size, -size, -size,
                size, -size, -size,
                -size, -size, size,
                size, -size, size
        };
    }
}
