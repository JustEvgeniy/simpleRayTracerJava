import Geometry.Vec3;

public class Sphere implements SceneObject {
    Vec3 center;
    double radius;

    public Sphere(Vec3 center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    @Override
    public IntersectionResult rayIntersect(Vec3 origin, Vec3 direction) {
        Vec3 L = center.subtract(origin);
        double tca = L.dotProduct(direction);
        double d2 = L.dotProduct(L) - tca * tca;
        if (d2 > radius * radius) {
            return new IntersectionResult(false);
        }

        double thc = Math.sqrt(radius * radius - d2);
        double intersectionDist = tca - thc;
        if (intersectionDist < 0)
            intersectionDist = tca + thc;
        return new IntersectionResult(intersectionDist >= 0, intersectionDist);
    }
}
