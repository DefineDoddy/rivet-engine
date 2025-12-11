package me.definedoddy.engine.entity.components;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import me.definedoddy.engine.entity.Component;
import me.definedoddy.engine.entity.Entity;

public class Transform implements Component {
    private Vector3f position;
    private Vector3f rotation;
    private Vector3f scale;

    public Transform() {
        this.position = new Vector3f(0, 0, 0);
        this.rotation = new Vector3f(0, 0, 0);
        this.scale = new Vector3f(1, 1, 1);
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
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
