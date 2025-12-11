package me.definedoddy.engine.physics.collision;

import org.joml.Vector3f;

public class BoundingBox {
    private final Vector3f min;
    private final Vector3f max;

    public BoundingBox(Vector3f min, Vector3f max) {
        this.min = new Vector3f(min);
        this.max = new Vector3f(max);
    }

    public static BoundingBox fromCenter(Vector3f center, Vector3f size) {
        Vector3f halfSize = new Vector3f(size).div(2);
        return new BoundingBox(new Vector3f(center).sub(halfSize), new Vector3f(center).add(halfSize));
    }

    public Vector3f getMin() {
        return min;
    }

    public Vector3f getMax() {
        return max;
    }

    public Vector3f getCenter() {
        return new Vector3f(min).add(max).div(2);
    }

    public Vector3f getSize() {
        return new Vector3f(max).sub(min);
    }

    public boolean intersects(BoundingBox other) {
        return min.x < other.max.x && max.x > other.min.x &&
                min.y < other.max.y && max.y > other.min.y &&
                min.z < other.max.z && max.z > other.min.z;
    }

    public boolean contains(BoundingBox other) {
        return min.x < other.min.x && max.x > other.max.x &&
                min.y < other.min.y && max.y > other.max.y &&
                min.z < other.min.z && max.z > other.max.z;
    }

    public boolean contains(Vector3f point) {
        return point.x > min.x && point.x < max.x &&
               point.y > min.y && point.y < max.y &&
               point.z > min.z && point.z < max.z;
    }

    public BoundingBox set(BoundingBox other) {
        min.set(other.min);
        max.set(other.max);
        return this;
    }

    public BoundingBox translate(Vector3f translation) {
        min.add(translation);
        max.add(translation);
        return this;
    }

    public BoundingBox rotate(Vector3f rotation) {
        min.rotateX(rotation.x).rotateY(rotation.y).rotateZ(rotation.z);
        max.rotateX(rotation.x).rotateY(rotation.y).rotateZ(rotation.z);
        return this;
    }

    public BoundingBox scale(Vector3f mul) {
        min.mul(mul);
        max.mul(mul);
        return this;
    }

    public BoundingBox scale(float mul) {
        min.mul(mul);
        max.mul(mul);
        return this;
    }

    public BoundingBox div(Vector3f div) {
        min.div(div);
        max.div(div);
        return this;
    }

    public BoundingBox div(float div) {
        min.div(div);
        max.div(div);
        return this;
    }

    public BoundingBox min(Vector3f min) {
        Vector3f center = getCenter();

        if (min.x < this.min.x) {
            this.min.x = center.x - min.x;
            this.max.x = center.x + min.x;
        }

        if (min.y < this.min.y) {
            this.min.y = center.y - min.y;
            this.max.y = center.y + min.y;
        }

        if (min.z < this.min.z) {
            this.min.z = center.z - min.z;
            this.max.z = center.z + min.z;
        }

        return this;
    }

    public BoundingBox max(Vector3f max) {
        Vector3f center = getCenter();

        if (max.x > this.max.x) {
            this.min.x = center.x - max.x;
            this.max.x = center.x + max.x;
        }

        if (max.y > this.max.y) {
            this.min.y = center.y - max.y;
            this.max.y = center.y + max.y;
        }

        if (max.z > this.max.z) {
            this.min.z = center.z - max.z;
            this.max.z = center.z + max.z;
        }

        return this;
    }

    public Vector3f[] getVertices() {
        Vector3f[] vertices = new Vector3f[8];
        vertices[0] = new Vector3f(min.x, min.y, min.z);
        vertices[1] = new Vector3f(max.x, min.y, min.z);
        vertices[2] = new Vector3f(min.x, max.y, min.z);
        vertices[3] = new Vector3f(max.x, max.y, min.z);
        vertices[4] = new Vector3f(min.x, min.y, max.z);
        vertices[5] = new Vector3f(max.x, min.y, max.z);
        vertices[6] = new Vector3f(min.x, max.y, max.z);
        vertices[7] = new Vector3f(max.x, max.y, max.z);
        return vertices;
    }

    @Override
    public BoundingBox clone() {
        return new BoundingBox(new Vector3f(min), new Vector3f(max));
    }
}
