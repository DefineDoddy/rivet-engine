package org.rivetengine.entity.components;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import org.rivetengine.entity.Entity;
import org.rivetengine.entity.component.Component;

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

    public Transform lookingAt(Vector3f target, Vector3f up) {
        Matrix4f lookAt = new Matrix4f().lookAt(position, target, up);
        Matrix4f rotationMatrix = new Matrix4f();
        lookAt.invert(rotationMatrix);
        rotationMatrix.getUnnormalizedRotation(new Quaternionf()).getEulerAnglesXYZ(rotation);
        return this;
    }

    public Matrix4f getLocalMatrix() {
        Matrix4f matrix = new Matrix4f().identity();
        matrix.translate(position);
        matrix.rotateXYZ(rotation.x, rotation.y, rotation.z);
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
