import Geometry.Vec3;

public class Light {
    public Vec3 position;
    public double intensity;

    public Light(Vec3 position, double intensity) {
        this.position = position;
        this.intensity = intensity;
    }
}
