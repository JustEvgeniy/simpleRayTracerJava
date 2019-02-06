package Renderer;

import Geometry.Vec3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scene {
    final List<Light> lights = new ArrayList<Light>();
    final List<Sphere> spheres = new ArrayList<Sphere>();
    private final Map<String, Material> materialMap = new HashMap<String, Material>();
    Envmap envmap = new Envmap();

//    public void addMaterial(String name, Vec3 diffuseColor) {
//        materialMap.put(name, new Material(1, new double[]{1, 0, 0, 0}, diffuseColor, 0));
//    }

    public void addMaterial(String name,
                            double refractiveIndex,
                            double a1, double a2, double a3, double a4,
                            Vec3 diffuseColor,
                            double specularExponent) {
        materialMap.put(name, new Material(refractiveIndex, new double[]{a1, a2, a3, a4}, diffuseColor, specularExponent));
    }

    public void addLight(double x, double y, double z, double i) {
        lights.add(new Light(new Vec3(x, y, z), i));
    }

    public void addSphere(double x, double y, double z, double r, String m) {
        spheres.add(new Sphere(new Vec3(x, y, z), r, materialMap.get(m)));
    }

    public void loadEnvmap(String filename) {
        envmap = new Envmap(filename);
    }

    public List<String> getMaterialNames() {
        return new ArrayList<>(materialMap.keySet());
    }
}
