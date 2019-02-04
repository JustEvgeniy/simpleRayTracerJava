package Geometry;

public abstract class Vector {
    protected double[] data;
    public static final double EPS = 1e-9;

    public abstract Vector add(Vector vector);

    public abstract Vector subtract(Vector vector);

    public abstract Vector multiply(double number);

    public Vector inverse() {
        return multiply(-1.0);
    }

    public abstract double length();

    public Vector normalize(double newLength) {
        return multiply(newLength / length());
    }

    public Vector normalize() {
        return normalize(1.0);
    }

    public abstract double dotProduct(Vector vector);

    @Override
    public abstract boolean equals(Object o);

    @Override
    public abstract int hashCode();

    @Override
    public abstract String toString();

    public double getMax() {
        double m = data[0];
        for (int i = 1; i < data.length; i++) {
            m = Math.max(m,data[i]);
        }
        return m;
    }
}
