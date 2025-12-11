package me.definedoddy.toolkit.maths;

public class Vec3 {
    public float x;
    public float y;
    public float z;

    public Vec3() {
        this(0, 0, 0);
    }

    public Vec3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3 add(Vec3 other) {
        return new Vec3(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    public Vec3 subtract(Vec3 other) {
        return new Vec3(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    public Vec3 scale(float scalar) {
        return new Vec3(this.x * scalar, this.y * scalar, this.z * scalar);
    }

    public float dot(Vec3 other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    public Vec3 cross(Vec3 other) {
        return new Vec3(
                this.y * other.z - this.z * other.y,
                this.z * other.x - this.x * other.z,
                this.x * other.y - this.y * other.x);
    }

    public float magnitude() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    public Vec3 normalize() {
        float mag = magnitude();
        if (mag == 0) {
            throw new ArithmeticException("Cannot normalize a zero-length vector");
        }
        return new Vec3(x / mag, y / mag, z / mag);
    }

    @Override
    public String toString() {
        return "Vec3{" + "x=" + x + ", y=" + y + ", z=" + z + "}";
    }
}
