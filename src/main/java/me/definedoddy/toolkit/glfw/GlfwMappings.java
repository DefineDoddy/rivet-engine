package me.definedoddy.toolkit.glfw;

import static java.sql.Types.NULL;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;

public class GlfwMappings {
    public static int toGlfwBoolean(boolean value) {
        return value ? GLFW_TRUE : GLFW_FALSE;
    }

    public static boolean isNull(long value) {
        return value == NULL;
    }
}
