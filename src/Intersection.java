import Geometry.Vec3;

public class Intersection {
    public double distance;
    public Vec3 hit;
    public Vec3 normal;
    public Material material;

    public Intersection(double distance) {
        this.distance = distance;
    }

    public Intersection(Vec3 hit, Vec3 normal, Material material) {
        this.hit = hit;
        this.normal = normal;
        this.material = material;
    }
}
