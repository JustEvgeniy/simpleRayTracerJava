import Geometry.Vec3;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    public List<Light> lights = new ArrayList<>();
    public List<Sphere> spheres = new ArrayList<>();

    void addLight(double x, double y, double z, double i) {
        lights.add(new Light(new Vec3(x, y, z), i));
    }

    void addSphere(double x, double y, double z, double r, Material m) {
        spheres.add(new Sphere(new Vec3(x, y, z), r, m));
    }
}
