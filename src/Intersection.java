import Geometry.Vec3;

public class Intersection {
    public boolean isIntersection;
    public double distance;
    public Vec3 hit;
    public Vec3 normal;
    public Material material;

    public Intersection() {
        this.isIntersection = false;
    }

    public Intersection(double distance) {
        this.isIntersection = true;
        this.distance = distance;
    }

    public Intersection(Vec3 hit, Vec3 normal, Material material) {
        this.isIntersection = true;
        this.hit = hit;
        this.normal = normal;
        this.material = material;
    }
}
