package Geometry;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class Vec2Test {
    @Test
    void testGet() {
        Vec2 vector2 = new Vec2(4.0, -71.0);

        assertEquals(4, vector2.getX());
        assertEquals(-71, vector2.getY());
    }

    @Test
    void add() {
        Vec2 vector1 = new Vec2(3.0, 2.5);
        Vec2 vector2 = new Vec2(-1.2, 4.0);

        assertEquals(new Vec2(1.8, 6.5), vector1.add(vector2));
    }

    @Test
    void subtract() {
        Vec2 vector1 = new Vec2(3.0, 2.5);
        Vec2 vector2 = new Vec2(-1.2, 4.0);

        assertEquals(new Vec2(4.2, -1.5), vector1.sub(vector2));
    }

    @Test
    void multiply() {
        Vec2 vector2 = new Vec2(3.333, -123.0);

        assertEquals(new Vec2(-9.999, 369.0), vector2.mul(-3.0));
    }

    @Test
    void inverse() {
        Vec2 vector2 = new Vec2(0.1234, -43.0);

        assertEquals(new Vec2(-0.1234, 43.0), vector2.inverse());
    }

    @Test
    void length() {
        Vec2 vector2 = new Vec2(3.0, 4.0);

        assertEquals(5.0, vector2.length());
    }

    @Test
    void normalize() {
        Vec2 vector2 = new Vec2(3.0, 4.0);

        assertEquals(new Vec2(0.6, 0.8), vector2.normalize());
        assertEquals(new Vec2(1.8, 2.4), vector2.normalize(3.0));
        assertEquals(new Vec2(0.3, 0.4), vector2.normalize(1 / 2.0));
    }

    @Test
    void dotProduct() {
        Vec2 vector1 = new Vec2(3.0, 4.0);
        Vec2 vector2 = new Vec2(0.1234, -43.0);

        assertEquals(-171.6298, vector1.dot(vector2));
    }

    @Test
    void testMultipleOperations() {
        Vec2 vector = new Vec2(1.0, 1.0);
        Vec2 vector2 = new Vec2(.34, .45);
        Vec2 vectorAdd = vector.add(vector2);

        assertEquals(new Vec2(1.0, 1.0), vector);
        assertEquals(new Vec2(.34, .45), vector2);
        assertEquals(new Vec2(1.34, 1.45), vectorAdd);

        Vec2 vectorSub = vector.sub(vector2);

        assertEquals(new Vec2(1.0, 1.0), vector);
        assertEquals(new Vec2(.34, .45), vector2);
        assertEquals(new Vec2(1.34, 1.45), vectorAdd);
        assertEquals(new Vec2(0.66, 0.55), vectorSub);
    }

    @Test
    void testEqualsHashString() {
        Vec2 vec2 = new Vec2(1., 2.);
        Vec2 vec2eq = new Vec2(1., 2.);
        Vec2 vec2neq = new Vec2(1.1, 2.1);

        assertEquals(vec2, vec2eq);
        assertNotEquals(vec2, vec2neq);
        assertEquals(Objects.hash(vec2.getX(), vec2.getY()), vec2.hashCode());
        assertEquals("Vec2{data=[1.0, 2.0]}", vec2.toString());
        assertEquals("Vec2{data=[0.4472135954999579, 0.8944271909999159]}", vec2.normalize().toString());
    }
}