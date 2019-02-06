import Geometry.Vec3;

public class Sphere implements SceneObject {
    public Vec3 center;
    public double radius;
    public Material material;

    public Sphere(Vec3 center, double radius, Material material) {
        this.center = center;
        this.radius = radius;
        this.material = material;
    }

    @Override
    public Intersection rayIntersect(Vec3 origin, Vec3 direction) {
        Vec3 L = center.subtract(origin);
        double tca = L.dotProduct(direction);
        double d2 = L.dotProduct(L) - tca * tca;
        if (d2 > radius * radius) {
            return null;
        }

        double thc = Math.sqrt(radius * radius - d2);
        double intersectionDist = tca - thc;
        if (intersectionDist < 0)
            intersectionDist = tca + thc;
        if (intersectionDist < 0)
            return null;
        return new Intersection(intersectionDist);
    }
}
