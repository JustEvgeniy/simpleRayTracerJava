package Renderer;

import Geometry.Vec3;

class Light {
    final Vec3 pos;
    final double intensity;

    Light(Vec3 position, double intensity) {
        this.pos = position;
        this.intensity = intensity;
    }
}
