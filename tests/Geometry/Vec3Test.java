package Geometry;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class Vec3Test {
    @Test
    void testGet() {
        Vec3 vec3 = new Vec3(4.0, -71.0, 14.134);

        assertEquals(4, vec3.getX());
        assertEquals(-71, vec3.getY());
        assertEquals(14.134, vec3.getZ());
    }

    @Test
    void add() {
        Vec3 vector1 = new Vec3(3.0, 2.5, 1 / 3.);
        Vec3 vector2 = new Vec3(-1.2, 4.0, 1 / 3.);

        assertEquals(new Vec3(1.8, 6.5, 2 / 3.), vector1.add(vector2));
    }

    @Test
    void subtract() {
        Vec3 vector1 = new Vec3(3.0, 2.5, 2 / 3.);
        Vec3 vector2 = new Vec3(-1.2, 4.0, 4 / 3.);

        assertEquals(new Vec3(4.2, -1.5, -2 / 3.), vector1.sub(vector2));
    }

    @Test
    void multiply() {
        Vec3 vec3 = new Vec3(3.333, -123.0, 1.2345);

        assertEquals(new Vec3(-9.999, 369.0, -3.7035), vec3.mul(-3.0));
    }

    @Test
    void inverse() {
        Vec3 vec3 = new Vec3(0.1234, -43.0, 2 / 3.);

        assertEquals(new Vec3(-0.1234, 43.0, 2 / -3.), vec3.inverse());
    }

    @Test
    void length() {
        Vec3 vec3 = new Vec3(3.0, 4.0, 2 / 3.);

        assertEquals(Math.sqrt(229 / 9.), vec3.length());
    }

    @Test
    void normalize() {
        Vec3 vec3 = new Vec3(3.0, 4.0, 5.0);

        assertEquals(
                new Vec3(3 / (5 * Math.sqrt(2)), 2 * Math.sqrt(2) / 5, 1 / Math.sqrt(2)),
                vec3.normalize()
        );
        assertEquals(
                new Vec3(9 / (5 * Math.sqrt(2)), 6 * Math.sqrt(2) / 5, 3 / Math.sqrt(2)),
                vec3.normalize(3.)
        );
        assertEquals(
                new Vec3(3 / (10 * Math.sqrt(2)), 2 * Math.sqrt(2) / 10, 1 / (2 * Math.sqrt(2))),
                vec3.normalize(1 / 2.)
        );
    }

    @Test
    void dotProduct() {
        Vec3 vector1 = new Vec3(3.0, 4.0, 5);
        Vec3 vector2 = new Vec3(0.1234, -43.0, .6);

        assertEquals(-168.6298, vector1.dot(vector2));
    }

    @Test
    void crossProduct() {
        Vec3 vector1 = new Vec3(3.0, 4.0, 5);
        Vec3 vector2 = new Vec3(0.1234, -43.0, .6);

        assertEquals(new Vec3(217.4, -1.183, -129.4936), vector1.crossProduct(vector2));
    }

    @Test
    void testMultipleOperations() {
        Vec3 vector = new Vec3(1.0, 1.0, 1.0);
        Vec3 vector2 = new Vec3(.34, .45, .56);
        Vec3 vectorAdd = vector.add(vector2);

        assertEquals(new Vec3(1.0, 1.0, 1.0), vector);
        assertEquals(new Vec3(.34, .45, .56), vector2);
        assertEquals(new Vec3(1.34, 1.45, 1.56), vectorAdd);

        Vec3 vectorSub = vector.sub(vector2);

        assertEquals(new Vec3(1.0, 1.0, 1.0), vector);
        assertEquals(new Vec3(.34, .45, .56), vector2);
        assertEquals(new Vec3(1.34, 1.45, 1.56), vectorAdd);
        assertEquals(new Vec3(0.66, 0.55, 0.44), vectorSub);
    }

    @Test
    void testEqualsHashString() {
        Vec3 vec3 = new Vec3(1., 2., 3.);
        Vec3 vec3eq = new Vec3(1., 2., 3.);
        Vec3 vec3neq = new Vec3(1.1, 2.1, 3.1);

        assertEquals(vec3, vec3eq);
        assertNotEquals(vec3, vec3neq);
        assertEquals(Objects.hash(vec3.getX(), vec3.getY(), vec3.getZ()), vec3.hashCode());
        assertEquals("Vec3{data=[1.0, 2.0, 3.0]}", vec3.toString());
    }
}