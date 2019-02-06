package Geometry;

import java.util.Arrays;
import java.util.Objects;

public final class Vec3 extends Vector {
    public Vec3(double x, double y, double z) {
        data = new double[]{x, y, z};
    }

    public double getX() {
        return data[0];
    }

    public double getY() {
        return data[1];
    }

    public double getZ() {
        return data[2];
    }

    @Override
    public Vec3 add(Vector vector) {
        return new Vec3(
                data[0] + vector.data[0],
                data[1] + vector.data[1],
                data[2] + vector.data[2]);
    }

    @Override
    public Vec3 sub(Vector vector) {
        return new Vec3(
                data[0] - vector.data[0],
                data[1] - vector.data[1],
                data[2] - vector.data[2]);
    }

    @Override
    public Vec3 mul(double number) {
        return new Vec3(
                data[0] * number,
                data[1] * number,
                data[2] * number);
    }

    @Override
    public double length() {
        return Math.sqrt(data[0] * data[0] + data[1] * data[1] + data[2] * data[2]);
    }

    @Override
    public double dot(Vector vector) {
        return data[0] * vector.data[0] +
                data[1] * vector.data[1] +
                data[2] * vector.data[2];
    }

    Vec3 crossProduct(Vec3 vec3) {
        return new Vec3(
                data[1] * vec3.data[2] - data[2] * vec3.data[1],
                data[2] * vec3.data[0] - data[0] * vec3.data[2],
                data[0] * vec3.data[1] - data[1] * vec3.data[0]);
    }

    @Override
    public Vec3 inverse() {
        return (Vec3) super.inverse();
    }

    @Override
    public Vec3 normalize(double newLength) {
        return (Vec3) super.normalize(newLength);
    }

    @Override
    public Vec3 normalize() {
        return (Vec3) super.normalize();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vec3 vec3 = (Vec3) o;
        return (Math.abs(data[0] - vec3.data[0]) < EPS) &&
                (Math.abs(data[1] - vec3.data[1]) < EPS) &&
                (Math.abs(data[2] - vec3.data[2]) < EPS);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data[0], data[1], data[2]);
    }

    @Override
    public String toString() {
        return "Vec3{" +
                "data=" + Arrays.toString(data) +
                '}';
    }
}
