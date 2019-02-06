package Renderer;

import Geometry.Vec3;

class Intersection {
    double dist;
    final Vec3 hit;
    final Vec3 N;
    final Material material;

    Intersection(Vec3 hit, Vec3 normal, Material material) {
        this.hit = hit;
        this.N = normal;
        this.material = material;
    }

    Intersection(double dist, Vec3 hit, Vec3 normal, Material material) {
        this.dist = dist;
        this.hit = hit;
        this.N = normal;
        this.material = material;
    }
}
