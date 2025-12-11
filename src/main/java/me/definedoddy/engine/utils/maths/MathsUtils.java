package me.definedoddy.engine.utils.maths;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import me.definedoddy.engine.entity.components.Camera;

public class MathsUtils {
    public static Matrix4f createTransformationMatrix(Vector3f position, Vector3f rotation, Vector3f scale) {
        Matrix4f matrix = new Matrix4f();
        matrix.identity();
        matrix.translate(position);
        matrix.rotateX((float) Math.toRadians(rotation.x));
        matrix.rotateY((float) Math.toRadians(rotation.y));
        matrix.rotateZ((float) Math.toRadians(rotation.z));
        matrix.scale(scale);
        return matrix;
    }

    public static Matrix4f createViewMatrix(Camera camera) {
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.identity();
        viewMatrix.rotateX((float) Math.toRadians(camera.getPitch()));
        viewMatrix.rotateY((float) Math.toRadians(camera.getYaw()));
        viewMatrix.rotateZ((float) Math.toRadians(camera.getRoll()));
        Vector3f cameraPos = camera.getPosition();
        Vector3f negativeCameraPos = new Vector3f(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        viewMatrix.translate(negativeCameraPos);
        return viewMatrix;
    }
}
