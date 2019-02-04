import Geometry.Vec3;
import Geometry.Vector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SphereTest {

    @Test
    void testRayIntersect() {
        Sphere sphere1 = new Sphere(new Vec3(3., 0, 0), 1.);

        Vec3 origin = new Vec3(0., 0., 0.);
        Vec3 direction = new Vec3(1., 0., 0.);

        IntersectionResult res = sphere1.rayIntersect(origin, direction);

        assertTrue(res.intersection);
        assertEquals(2., res.distance, Vector.EPS);
    }
}