package org.rivetengine.entity.components;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import org.rivetengine.entity.Entity;
import org.rivetengine.entity.component.Component;
import org.rivetengine.toolkit.maths.Directions;

public class Transform implements Component {
    public Vector3f position;
    public Vector3f rotation;
    public Vector3f scale;

    public Transform() {
        this(new Vector3f(), new Vector3f(), new Vector3f(1, 1, 1));
    }

    public Transform(Vector3f position, Vector3f rotation, Vector3f scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public static Transform fromXYZ(float x, float y, float z) {
        return new Transform(new Vector3f(x, y, z), new Vector3f(), new Vector3f(1, 1, 1));
    }

    public static Transform identity() {
        return new Transform(new Vector3f(), new Vector3f(), new Vector3f(1, 1, 1));
    }

    public Transform lookingAt(float x, float y, float z) {
        return lookingAt(new Vector3f(x, y, z), Directions.UP);
    }

    public Transform lookingAt(Vector3f target, Vector3f up) {
        Vector3f dir = new Vector3f(target).sub(position).normalize();
        if (Math.abs(dir.dot(up)) > 0.999f) {
            up = Directions.FORWARD;
        }

        Matrix4f lookAt = new Matrix4f().lookAt(position, target, up);
        Matrix4f rotationMatrix = new Matrix4f();
        lookAt.invert(rotationMatrix);
        rotationMatrix.getUnnormalizedRotation(new Quaternionf()).getEulerAnglesXYZ(rotation);
        rotation.set((float) Math.toDegrees(rotation.x), (float) Math.toDegrees(rotation.y),
                (float) Math.toDegrees(rotation.z));
        return this;
    }

    public Matrix4f getLocalMatrix() {
        Matrix4f matrix = new Matrix4f().identity();
        matrix.translate(position);
        matrix.rotateXYZ((float) Math.toRadians(rotation.x), (float) Math.toRadians(rotation.y),
                (float) Math.toRadians(rotation.z));
        matrix.scale(scale);
        return matrix;
    }

    public Matrix4f getWorldMatrix(Entity entity) {
        Matrix4f local = getLocalMatrix();

        if (entity.getParent() == null) {
            return local;
        }

        Transform parentTransform = entity.getParent().getComponent(Transform.class);

        if (parentTransform == null) {
            return local;
        }

        return parentTransform.getWorldMatrix(entity.getParent()).mul(local);
    }
}
