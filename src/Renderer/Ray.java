package Renderer;

import Geometry.Vec3;

class Ray {
    final Vec3 origin;
    final Vec3 dir;

    Ray(Vec3 origin, Vec3 direction) {
        this.origin = origin;
        this.dir = direction;
    }
}
