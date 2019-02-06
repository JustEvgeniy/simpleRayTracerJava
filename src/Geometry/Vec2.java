package Geometry;

import java.util.Arrays;
import java.util.Objects;

public final class Vec2 extends Vector {
    public Vec2(double x, double y) {
        data = new double[]{x, y};
    }

    public double getX() {
        return data[0];
    }

    public double getY() {
        return data[1];
    }

    @Override
    public Vec2 add(Vector vector) {
        return new Vec2(
                data[0] + vector.data[0],
                data[1] + vector.data[1]);
    }

    @Override
    public Vec2 sub(Vector vector) {
        return new Vec2(
                data[0] - vector.data[0],
                data[1] - vector.data[1]);
    }

    @Override
    public Vec2 mul(double number) {
        return new Vec2(
                data[0] * number,
                data[1] * number);
    }

    @Override
    public Vec2 inverse() {
        return (Vec2) super.inverse();
    }

    @Override
    public Vec2 normalize(double newLength) {
        return (Vec2) super.normalize(newLength);
    }

    @Override
    public Vec2 normalize() {
        return (Vec2) super.normalize();
    }

    @Override
    public double length() {
        return Math.hypot(data[0], data[1]);
    }

    @Override
    public double dot(Vector vector) {
        return data[0] * vector.data[0] + data[1] * vector.data[1];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vec2 vec2 = (Vec2) o;
        return (Math.abs(data[0] - vec2.data[0]) < EPS) &&
                (Math.abs(data[1] - vec2.data[1]) < EPS);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data[0], data[1]);
    }

    @Override
    public String toString() {
        return "Vec2{" +
                "data=" + Arrays.toString(data) +
                '}';
    }
}