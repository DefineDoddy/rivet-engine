package me.definedoddy.engine.rendering.object;

import me.definedoddy.toolkit.debug.DebugLog;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_SHORT;
import static org.lwjgl.opengl.GL20.*;

public class Attribute {
    private final int index;
    private final int size;
    private final int type;

    private final int bytesPerVertex;

    public Attribute(int index, int size, int type) {
        this.index = index;
        this.size = size;
        this.type = type;

        this.bytesPerVertex = calcBytesPerVertex();
    }

    public void setEnabled(boolean enabled) {
        if (enabled) glEnableVertexAttribArray(index);
        else glDisableVertexAttribArray(index);
    }

    public void link(int offset, int stride) {
        glVertexAttribPointer(index, size, type, false, stride, offset);
    }

    private int calcBytesPerVertex() {
        if (type == GL_FLOAT || type == GL_UNSIGNED_INT || type == GL_INT) {
            return 4 * size;
        } else if (type == GL_SHORT || type == GL_UNSIGNED_SHORT) {
            return 2 * size;
        } else if (type == GL_BYTE || type == GL_UNSIGNED_BYTE) {
            return size;
        } else if (type == GL_UNSIGNED_INT_2_10_10_10_REV) {
            return 4;
        }
        DebugLog.error("Unsupported data type for VAO attribute: " + type);
        return 0;
    }

    public int getIndex() {
        return index;
    }

    public int getSize() {
        return size;
    }

    public int getType() {
        return type;
    }

    public int getBytesPerVertex() {
        return bytesPerVertex;
    }
}
