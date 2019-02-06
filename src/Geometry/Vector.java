package Geometry;

public abstract class Vector {
    double[] data;
    static final double EPS = 1e-9;

    public abstract Vector add(Vector vector);

    public abstract Vector sub(Vector vector);

    public abstract Vector mul(double number);

    public Vector inverse() {
        return mul(-1.0);
    }

    public abstract double length();

    public Vector normalize(double newLength) {
        return mul(newLength / length());
    }

    public Vector normalize() {
        return normalize(1.0);
    }

    public abstract double dot(Vector vector);

    @Override
    public abstract boolean equals(Object o);

    @Override
    public abstract int hashCode();

    @Override
    public abstract String toString();

    public double getMax() {
        double m = data[0];
        for (int i = 1; i < data.length; i++) {
            m = Math.max(m, data[i]);
        }
        return m;
    }
}
