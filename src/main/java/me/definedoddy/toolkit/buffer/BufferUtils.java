package me.definedoddy.toolkit.buffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class BufferUtils {
    public static ByteBuffer createByteBuffer(int capacity) {
        return ByteBuffer.allocateDirect(capacity).order(ByteOrder.nativeOrder());
    }

    public static FloatBuffer createFloatBuffer(float[] data) {
        FloatBuffer buffer = createByteBuffer(data.length << 2).asFloatBuffer();
        return buffer.put(data).flip();
    }
}
