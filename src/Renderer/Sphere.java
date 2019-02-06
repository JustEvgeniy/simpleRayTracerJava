package Renderer;

import Geometry.Vec3;

class Sphere implements SceneObject {
    private final Vec3 center;
    private final double radius;
    private final Material material;

    Sphere(Vec3 center, double radius, Material material) {
        this.center = center;
        this.radius = radius;
        this.material = material;
    }

    @Override
    public Intersection rayIntersect(Ray ray) {
        Vec3 L = center.sub(ray.origin);
        double tca = L.dot(ray.dir);
        double d2 = L.dot(L) - tca * tca;
        if (d2 > radius * radius) {
            return null;
        }

        double thc = Math.sqrt(radius * radius - d2);
        double intersectionDist = tca - thc;
        if (intersectionDist < 0)
            intersectionDist = tca + thc;
        if (intersectionDist < 0)
            return null;

        //hit = origin + direction * distance
        Vec3 hit = ray.origin.add(ray.dir.mul(intersectionDist));

        //normal = (hit - center).normalize()
        return new Intersection(intersectionDist, hit, hit.sub(center).normalize(), material);
    }
}
