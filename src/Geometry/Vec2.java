package Geometry;

import java.util.Arrays;
import java.util.Objects;

public class Vec2 extends Vector {
    public Vec2(double x, double y) {
        data = new double[2];
        data[0] = x;
        data[1] = y;
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
    public Vec2 subtract(Vector vector) {
        return new Vec2(
                data[0] - vector.data[0],
                data[1] - vector.data[1]);
    }

    @Override
    public Vec2 multiply(double number) {
        return new Vec2(
                data[0] * number,
                data[1] * number);
    }

    @Override
    public double length() {
        return Math.hypot(data[0], data[1]);
    }

    @Override
    public double dotProduct(Vector vector) {
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